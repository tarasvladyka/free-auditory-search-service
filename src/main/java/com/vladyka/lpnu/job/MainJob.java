package com.vladyka.lpnu.job;

import com.vladyka.lpnu.crawl.CrawlOptions;
import com.vladyka.lpnu.crawl.ScheduleCrawler;
import com.vladyka.lpnu.exception.SchedulePageParseException;
import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MainJob {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ScheduleCrawler scheduleCrawler;

    @Value("${parse.student.schedule.full-time.enabled}")
    private Boolean parseStudentScheduleFullTimeEnabled;
    @Value("${parse.student.schedule.part-time.enabled}")
    private Boolean parseStudentSchedulePartTimeEnabled;
    @Value("${parse.selective.schedule.enabled}")
    private Boolean parseSelectiveScheduleEnabled;
    @Value("${parse.post-graduate.schedule.full-time.enabled}")
    private Boolean parsePostGraduateScheduleFullTimeEnabled;
    @Value("${parse.post-graduate.schedule.part-time.enabled}")
    private Boolean parsePostGraduateSchedulePartTimeEnabled;

    @Value("${mode}")
    private String parseMode;

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        boolean failed = false;
        logger.info("[MainJob] Started crawling in {} mode", parseMode);
        try {
            if (parseStudentScheduleFullTimeEnabled) {
                scheduleCrawler.crawl(new CrawlOptions()
                        .setGroupType(GroupType.STUDENT_GROUP)
                        .setStudyForm(StudyForm.FULL_TIME));
            }
            if (parseStudentSchedulePartTimeEnabled) {
                scheduleCrawler.crawl(new CrawlOptions()
                        .setGroupType(GroupType.STUDENT_GROUP)
                        .setStudyForm(StudyForm.PART_TIME));
            }
            if (parseSelectiveScheduleEnabled) {
                scheduleCrawler.crawl(new CrawlOptions()
                        .setGroupType(GroupType.SELECTIVE_DISCIPLINES_GROUP)
                        .setStudyForm(StudyForm.FULL_TIME));
            }
            if (parsePostGraduateScheduleFullTimeEnabled) {
                scheduleCrawler.crawl(new CrawlOptions()
                        .setGroupType(GroupType.POST_GRADUATE_GROUP)
                        .setStudyForm(StudyForm.FULL_TIME));
            }
            if (parsePostGraduateScheduleFullTimeEnabled) {
                scheduleCrawler.crawl(new CrawlOptions()
                        .setGroupType(GroupType.POST_GRADUATE_GROUP)
                        .setStudyForm(StudyForm.PART_TIME));
            }
        } catch (SchedulePageParseException e) {
            logger.error(String.format(
                    "[MainJob] Exception occurred during parsing page at url:\n\n" +
                            "%s\n\n" +
                            "%s\n",
                    e.getUrl(),
                    e.getMessage()));
            failed = true;
        } catch (Exception e) {
            logger.error("[MainJob] Parsing failed. Unexpected exception occurred:", e);
            failed = true;
        }
        if (failed) {
            System.exit(0);
        }
        logger.info("[MainJob] Successfully finished crawling in {} mode", parseMode);
    }
}
