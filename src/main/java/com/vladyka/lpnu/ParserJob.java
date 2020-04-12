package com.vladyka.lpnu;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class ParserJob {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

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

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        logger.info("Started parsing");
        String instAbbr = "ІЕСК";
        String groupAbbr = "ЕЕ-21";
        Institute institute = instituteService.create(new Institute().setAbbr(instAbbr));
        Group group = groupService.create(new Group().setAbbr(groupAbbr).setInstitute(institute));
        parseSinglePage(institute, group);
//        try {
//            parseAndStoreData();
//        } catch (IOException e) {
//            logger.error("Error during parsing: ", e);
//        }
        logger.info("Finished parsing");
    }

    private void parseAndStoreData() throws IOException {
        List<String> institutes = parseService.parseInstitutes();
        instituteService.createAll(institutes);
        logger.info("Parsed and stored info for {} institutes", institutes.size());

        for (String institute : institutes) {
            List<String> instituteGroups = parseService.parseGroups(institute);
            groupService.createAllInInstitute(instituteGroups, institute);
            logger.info("Parsed and stored info for {} groups in institute {}", instituteGroups.size(), institute);
        }
        parseSchedulePages();
    }

    private void parseSchedulePages() {
        instituteService
                .findAll()
                .forEach((institute ->
                        groupService
                                .findAllByInstitute(institute.getId())
                                .forEach(group -> parseSinglePage(institute, group))));
    }

    private void parseSinglePage(Institute inst, Group group) {
        String instAbbr = inst.getAbbr();
        String groupAbbr = group.getAbbr();
        String urlToParse = urlProvider.getGroupScheduleUrl(instAbbr, groupAbbr);
        try {
            List<ParsedScheduleEntry> parsedData = parseService.parseGroupSchedule(instAbbr, groupAbbr);
            List<ScheduleEntry> entries = new LinkedList<>();
            parsedData.forEach(parsed -> entries.addAll(scheduleEntryBuilder.buildFrom(parsed, group)));
            scheduleEntryService.createAll(entries);
            logger.info("Parsed and stored schedule for institute {}, group {}, url = {}",
                    instAbbr,
                    groupAbbr,
                    urlToParse);
        } catch (SchedulePageParseException | IllegalArgumentException e) {
            logger.error(String.format(
                    "Exception occurred during parsing schedule:\n" +
                            "%s\n\n" +
                            "Exception: %s\n" +
                            "Consider testing this string and regex at https://regex101.com/\n",
                    urlToParse,
                    e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected exception occurred during parsing schedule at url: " + urlToParse, e);
        }
    }
}
