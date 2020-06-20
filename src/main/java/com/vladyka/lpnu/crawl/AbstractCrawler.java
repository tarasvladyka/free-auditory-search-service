package com.vladyka.lpnu.crawl;

import com.vladyka.lpnu.dto.ParsedScheduleEntry;
import com.vladyka.lpnu.exception.SchedulePageParseException;
import com.vladyka.lpnu.model.DateBasedScheduleEntry;
import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.model.ScheduleEntry;
import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.ScheduleType;
import com.vladyka.lpnu.model.enums.StudyForm;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.service.impl.ScheduleEntryServiceImpl;
import com.vladyka.lpnu.tools.ParseHelper;
import com.vladyka.lpnu.tools.ScheduleEntryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import static com.vladyka.lpnu.model.enums.ParseMode.DEMO;

public abstract class AbstractCrawler {

  protected Logger logger = LogManager.getLogger(getClass().getName());

  @Autowired
  private ScheduleEntryBuilder scheduleEntryBuilder;
  @Autowired
  private ScheduleEntryServiceImpl scheduleEntryService;
  @Autowired
  private ParseServiceImpl parseService;
  @Autowired
  private InstituteService instituteService;
  @Autowired
  private GroupService groupService;
  @Autowired
  private ParseHelper helper;

  @Value("${mode}")
  protected String parseMode;

  protected GroupType groupType;
  protected StudyForm studyForm;
  protected ScheduleType scheduleType;
  protected String logPrefix;
  protected String baseUrl;

  protected AbstractCrawler(String baseUrl, GroupType groupType, StudyForm studyForm, ScheduleType scheduleType) {
    this.groupType = groupType;
    this.studyForm = studyForm;
    this.baseUrl = baseUrl;
    this.scheduleType = scheduleType;
    this.logPrefix = MessageFormat.format(
            "[{0} for {1} ({2}) ]",
            scheduleType.getFriendlyName(),
            groupType.getFriendlyName(),
            studyForm.getFriendlyName());
  }

  public abstract void crawl();

  public String getLogPrefix() {
    return logPrefix;
  }

  protected void parseAndStoreInstitutes() {
    List<String> institutes = parseService.parseInstitutes(baseUrl);
    institutes.forEach(instituteService::createIfNotExists);

    logger.info("Parsed and stored {} institutes", institutes.size());
  }

  protected void parseAndStoreGroupsByInstitutes() {

    for (Institute institute : instituteService.findAll()) {

      String instAbbr = institute.getAbbr();
      String instUrl = getBaseInstituteUrl(instAbbr);

      List<String> groups = parseService.parseGroups(instUrl);
      groupService.createAllInInstitute(groups, instAbbr, groupType, studyForm);

      logger.info("Parsed and stored {} groups in institute {}", groups.size(), instAbbr);
      if (DEMO.name().equalsIgnoreCase(parseMode)) {
        break;
      }
    }
  }

  protected void parseAndStoreGroups() {
    List<String> groups = parseService.parseGroups(baseUrl);
    groupService.createAll(groups, groupType, studyForm);
    logger.info("Parsed and stored {} groups", groups.size());
  }

  protected void parseAndStoreRegularSchedule() {
    int total = groupService.findCount(groupType, studyForm);

    int counter = 0;
    for (Group group : groupService.findAll(groupType, studyForm)) {
      List<ParsedScheduleEntry> parsedEntries = crawlSinglePage(group.getAbbr(), total, counter);
      storeRegularEntries(parsedEntries, group);
      counter++;
      if (DEMO.name().equalsIgnoreCase(parseMode)) {
        break;
      }
    }
  }

  protected void parseAndStoreDateBasedSchedule() {
    int total = groupService.findCount(groupType, studyForm);

    int counter = 0;
    for (Group group : groupService.findAll(groupType, studyForm)) {
      List<ParsedScheduleEntry> parsedEntries = crawlSinglePage(group.getAbbr(), total, counter);
      storeDateBasedEntries(parsedEntries, group);
      counter++;
      if (DEMO.name().equalsIgnoreCase(parseMode)) {
        break;
      }
    }
  }

  private List<ParsedScheduleEntry> crawlSinglePage(String groupAbbr, int total, int counter) {
    Long startTime = System.currentTimeMillis();
    String scheduleUrl = getScheduleUrl(groupAbbr);

    List<ParsedScheduleEntry> parsedEntries;
    try {
      parsedEntries = parseService.parseGroupSchedule(scheduleUrl);
    } catch (Exception e) {
      throw new SchedulePageParseException(scheduleUrl, e.getMessage());
    }

    Long endTime = System.currentTimeMillis();
    counter++;
    printProgressLogs(groupAbbr, startTime, endTime, counter, total, scheduleUrl);

    return parsedEntries;
  }

  private void storeRegularEntries(List<ParsedScheduleEntry> entries, Group group) {
    List<ScheduleEntry> resultEntries = new LinkedList<>();
    entries.forEach(parsed ->
            resultEntries.addAll(scheduleEntryBuilder.buildRegularOne(parsed, group)));
    scheduleEntryService.createAllRegular(resultEntries);
  }

  private void storeDateBasedEntries(List<ParsedScheduleEntry> entries, Group group) {
    List<DateBasedScheduleEntry> resultEntries = new LinkedList<>();
    entries.forEach(parsed -> resultEntries.addAll(scheduleEntryBuilder.buildDateBased(parsed, group)));
    scheduleEntryService.createAllDateBased(resultEntries);
  }

  private void printProgressLogs(String groupAbbr, Long startTime, Long endTime, int counter, int total, String parseUrl) {
    double parseSpeed = (endTime - startTime) / 1000.0;
    double remainingTimeMin = (total - counter) * parseSpeed / 60;
    logger.info("[{}%] [{}/{}] [remaining ~ {} хв]: parsed and stored schedule for group {}, url = {}",
            String.format("%.1f", counter / (double) total * 100),
            counter,
            total,
            String.format("%.1f", remainingTimeMin),
            groupAbbr,
            parseUrl);
  }

  private String getBaseInstituteUrl(String instAbbr) {
    return baseUrl + "?institutecode_selective=" + instAbbr;
  }

  private String getScheduleUrl(String groupAbbr) {
    return baseUrl + "?institutecode_selective=All&edugrupabr_selective=" + groupAbbr;
  }
}
