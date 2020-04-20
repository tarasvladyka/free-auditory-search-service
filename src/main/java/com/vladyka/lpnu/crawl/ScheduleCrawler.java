package com.vladyka.lpnu.crawl;

import com.vladyka.lpnu.dto.ParsedScheduleEntry;
import com.vladyka.lpnu.exception.SchedulePageParseException;
import com.vladyka.lpnu.model.DateBasedScheduleEntry;
import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.model.ScheduleEntry;
import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.service.impl.ScheduleEntryServiceImpl;
import com.vladyka.lpnu.tools.Helper;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import com.vladyka.lpnu.tools.ScheduleEntryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

import static com.vladyka.lpnu.model.enums.GroupType.STUDENT_GROUP;
import static com.vladyka.lpnu.model.enums.ParseMode.DEMO;
import static com.vladyka.lpnu.model.enums.StudyForm.FULL_TIME;

@Component
public class ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;
    @Autowired
    private InstituteService instituteService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ScheduleEntryBuilder scheduleEntryBuilder;
    @Autowired
    private ScheduleEntryServiceImpl scheduleEntryService;
    @Autowired
    private Helper helper;
    @Autowired
    private ParseUrlProvider urlProvider;

    @Value("${mode}")
    private String parseMode;

    /**
     * 1. Only schedule for students now have dropdown with institute name
     */
    public void crawl(CrawlOptions crawlOptions) {
        validate(crawlOptions);

        if (STUDENT_GROUP.equals(crawlOptions.getGroupType())) {
            parseAndStoreInstitutes(crawlOptions);
        }
        parseAndStoreGroups(crawlOptions);
        crawlSchedules(crawlOptions);
    }

    private void crawlSchedules(CrawlOptions crawlOptions) {
        GroupType groupType = crawlOptions.getGroupType();
        StudyForm studyForm = crawlOptions.getStudyForm();

        logger.info("[{} schedule, {}] - Started crawling schedules", groupType, studyForm);
        int total = groupService.findCount(groupType, studyForm);
        int counter = 0;
        for (Group group : groupService.findAll(groupType, studyForm)) {
            crawlSinglePage(crawlOptions, group.getAbbr(), total, counter);
            counter++;
            if (DEMO.name().equalsIgnoreCase(parseMode)) {
                break;
            }
        }
        logger.info("[{} schedule, {}] - Finished crawling schedules, total = {}", groupType,
                studyForm, total);
    }

    private void crawlSinglePage(CrawlOptions options, String groupAbbr, int total, int counter) {
        Long startTime = System.currentTimeMillis();
        StudyForm studyForm = options.getStudyForm();

        GroupType groupType = options.getGroupType();
        Group targetGroup = groupService.find(groupAbbr, studyForm, groupType);
        String url = urlProvider.getGroupScheduleUrl(options.getGroupType(), options.getStudyForm(), groupAbbr);
        try {
            List<ParsedScheduleEntry> parsedEntries = parseService.parseGroupSchedule(url);
            if (FULL_TIME.equals(options.getStudyForm())) {
                storeRegularEntries(parsedEntries, targetGroup);
            } else {
                storeDateBasedEntries(parsedEntries, targetGroup);
            }
        } catch (Exception e) {
            throw new SchedulePageParseException(url, e.getMessage());
        }
        Long endTime = System.currentTimeMillis();
        counter++;
        helper.printProgressLogs(groupAbbr, startTime, endTime, counter, total, url);
    }

    private void storeDateBasedEntries(List<ParsedScheduleEntry> entries, Group group) {
        List<DateBasedScheduleEntry> resultEntries = new LinkedList<>();
        entries.forEach(parsed ->
                resultEntries.addAll(scheduleEntryBuilder.buildDateBased(parsed, group)));
        scheduleEntryService.createAllDateBased(resultEntries);
    }

    private void storeRegularEntries(List<ParsedScheduleEntry> entries, Group group) {
        List<ScheduleEntry> resultEntries = new LinkedList<>();
        entries.forEach(parsed ->
                resultEntries.addAll(scheduleEntryBuilder.buildRegularOne(parsed, group)));
        scheduleEntryService.createAllRegular(resultEntries);
    }

    private void parseAndStoreGroups(CrawlOptions options) {
        GroupType groupType = options.getGroupType();
        StudyForm studyForm = options.getStudyForm();

        if (STUDENT_GROUP.equals(groupType)) {
            parseAndStoreGroupsByInstitute(options);
        } else {
            logger.info("[{} schedule, {}] - Started crawling groups dropDown", groupType, studyForm);
            List<String> groups = parseService.parseGroups(urlProvider.getBaseUrl(groupType, studyForm));
            groupService.createAll(groups, groupType, studyForm);
            logger.info("[{} schedule, {}] - Finished crawling groups dropDown, total {} groups", groupType, studyForm, groups.size());
        }
    }

    private void parseAndStoreGroupsByInstitute(CrawlOptions options) {
        int totalGroups = 0;
        GroupType groupType = options.getGroupType();
        StudyForm studyForm = options.getStudyForm();

        logger.info("[{} schedule, {}] - Started crawling group dropDowns for institutes", groupType,
                studyForm);
        for (Institute institute : instituteService.findAll()) {
            String instAbbr = institute.getAbbr();
            List<String> groups = parseService.parseGroups(urlProvider.getGroupsUrl(groupType, studyForm, instAbbr));
            groupService.createAllInInstitute(groups, instAbbr, STUDENT_GROUP, studyForm);
            totalGroups += groups.size();
            logger.info("[{} schedule, {}] - Parsed and stored {} groups in institute {}", groupType, studyForm, groups.size(), instAbbr);
            if (DEMO.name().equalsIgnoreCase(parseMode)) {
                break;
            }
        }
        logger.info("[{} schedule, {}] - Finished crawling groups dropDowns, total {} groups", groupType, studyForm, totalGroups);
    }

    private void parseAndStoreInstitutes(CrawlOptions options) {
        logger.info("[{} schedule, {}] - Started crawling institutes dropDown", options.getGroupType(), options.getStudyForm());
        List<String> institutes = parseService.parseInstitutes(urlProvider.getBaseUrl(
                options.getGroupType(),
                options.getStudyForm()));
        institutes.forEach(instituteService::createIfNotExists);
        logger.info("[{} schedule, {}] - Finished crawling institutes dropDown, total {} institutes",
                options.getGroupType(),
                options.getStudyForm(),
                institutes.size());
    }

    private void validate(CrawlOptions crawlOptions) {
        Assert.notNull(crawlOptions.getGroupType(), "Group type should be specified");
        Assert.notNull(crawlOptions.getStudyForm(), "Study form should be specified");
    }
}
