package com.vladyka.lpnu.crawl;

import com.vladyka.lpnu.dto.ParsedScheduleEntry;
import com.vladyka.lpnu.exception.SchedulePageParseException;
import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.model.ScheduleEntry;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.service.impl.ScheduleEntryServiceImpl;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import com.vladyka.lpnu.tools.ScheduleEntryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StudentScheduleCrawler implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;
    @Autowired
    private InstituteService instituteService;
    @Autowired
    private ParseUrlProvider urlProvider;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ScheduleEntryBuilder scheduleEntryBuilder;
    @Autowired
    private ScheduleEntryServiceImpl scheduleEntryService;

    private static Integer counter;
    private static Integer fails;
    private static Integer total;

    @Override
    public void crawl() {
        logger.info("Started crawling students schedules");
        counter = 0;
        fails = 0;
        total = groupService.findTotalCount();
        for (Institute institute : instituteService.findAll()) {
            for (Group group : groupService.findAllByInstitute(institute.getId())) {
                String urlToParse = urlProvider.getGroupScheduleUrl(institute.getAbbr(), group.getAbbr());

                Long startTime = System.currentTimeMillis();
                crawlSinglePage(institute.getAbbr(), group.getAbbr());
                Long endTime = System.currentTimeMillis();
                double parseSpeed = (endTime - startTime) / 1000.0;
                logger.info("[{}%] [{}/{}] [remaining ~ {} хв]: parsed and stored schedule for institute {}, group {}, url = {}",
                        String.format("%.1f", counter / (double) total * 100),
                        counter,
                        total,
                        String.format("%.1f", (total - counter) * parseSpeed / 60),
                        institute.getAbbr(),
                        group.getAbbr(),
                        urlToParse);
                counter++;
                break;
            }
            break;
        }
        logger.info("Finished crawling students schedules, total = {}, fails = {}", total, fails);
    }

    public void crawlSinglePage(String instAbbr, String groupAbbr) {
        Group targetGroup = groupService.findByAbbrAndInstituteAbbr(groupAbbr, instAbbr);
        String urlToParse = urlProvider.getGroupScheduleUrl(instAbbr, groupAbbr);

        try {
            List<ParsedScheduleEntry> parsedEntries = parseService.parseGroupSchedule(instAbbr, groupAbbr);
            List<ScheduleEntry> resultEntries = new LinkedList<>();
            parsedEntries.forEach(parsed -> resultEntries.addAll(
                    scheduleEntryBuilder.buildFrom(parsed, targetGroup)));
            scheduleEntryService.createAll(resultEntries);
        } catch (SchedulePageParseException | IllegalArgumentException e) {
            logger.error(String.format(
                    "Exception occurred during parsing schedule:\n" +
                            "%s\n\n" +
                            "Exception: %s\n",
                    urlToParse,
                    e.getMessage()));
            fails++;
        } catch (Exception e) {
            logger.error("Unexpected exception occurred during parsing schedule at url: " + urlToParse, e);
            fails++;
        }
    }
}
