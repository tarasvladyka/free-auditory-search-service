package com.vladyka.lpnu.crawl.schedule.student.selective;

import com.vladyka.lpnu.crawl.schedule.student.ScheduleCrawler;
import com.vladyka.lpnu.dto.ParsedScheduleEntry;
import com.vladyka.lpnu.exception.SchedulePageParseException;
import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.ScheduleEntry;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.service.impl.ScheduleEntryServiceImpl;
import com.vladyka.lpnu.tools.Helper;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import com.vladyka.lpnu.tools.ScheduleEntryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.vladyka.lpnu.model.enums.GroupType.SELECTIVE_DISCIPLINES_GROUP;
import static com.vladyka.lpnu.model.enums.ParseMode.DEMO;
import static com.vladyka.lpnu.model.enums.StudyForm.FULL_TIME;

@Service
public class SelectiveScheduleCrawler implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;
    @Autowired
    private ParseUrlProvider urlProvider;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ScheduleEntryBuilder scheduleEntryBuilder;
    @Autowired
    private ScheduleEntryServiceImpl scheduleEntryService;
    @Autowired
    private SelectiveScheduleGroupsCrawler groupsCrawler;
    @Autowired
    private Helper helper;

    @Value("${mode}")
    private String parseMode;

    @Override
    public void crawl() {
        groupsCrawler.crawl();
        logger.info("[Selective schedule] - Started crawling schedules");
        int total = groupService.findCount(SELECTIVE_DISCIPLINES_GROUP, FULL_TIME);
        int counter = 0;
        for (Group group : groupService.findAll(SELECTIVE_DISCIPLINES_GROUP, FULL_TIME)) {
            crawlSinglePage(group.getAbbr(), total, counter);
            counter++;
            if (DEMO.name().equalsIgnoreCase(parseMode)) {
                break;
            }
        }
        logger.info("[Selective schedule] - Finished crawling schedules, total = {}", total);
    }

    private void crawlSinglePage(String groupAbbr, int total, int counter) {
        Long startTime = System.currentTimeMillis();
        Group targetGroup = groupService.find(groupAbbr, FULL_TIME, SELECTIVE_DISCIPLINES_GROUP);
        String urlToParse = urlProvider.getSelectiveGroupScheduleUrl(groupAbbr);
        try {
            List<ParsedScheduleEntry> parsedEntries = parseService.parseGroupSchedule(urlToParse);
            List<ScheduleEntry> resultEntries = new LinkedList<>();
            parsedEntries.forEach(parsed ->
                    resultEntries.addAll(scheduleEntryBuilder.buildRegularOne(parsed, targetGroup)));
            scheduleEntryService.createAllRegular(resultEntries);
        } catch (Exception e) {
            throw new SchedulePageParseException(urlToParse, e.getMessage());
        }
        Long endTime = System.currentTimeMillis();
        counter++;
        helper.printProgressLogs(groupAbbr, startTime, endTime, counter, total, urlToParse);
    }
}
