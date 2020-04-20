package com.vladyka.lpnu.job;

import com.vladyka.lpnu.crawl.schedule.student.fulltime.StudentScheduleCrawlerFT;
import com.vladyka.lpnu.crawl.schedule.student.parttime.StudentScheduleCrawlerPT;
import com.vladyka.lpnu.crawl.schedule.student.selective.SelectiveScheduleCrawler;
import com.vladyka.lpnu.exception.SchedulePageParseException;
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
    private StudentScheduleCrawlerFT crawlerFT;
    @Autowired
    private StudentScheduleCrawlerPT crawlerPT;
    @Autowired
    private SelectiveScheduleCrawler selectiveScheduleCrawler;

    @Value("${parse.student.schedule.full-time.enabled}")
    private Boolean parseStudentScheduleFullTimeEnabled;

    @Value("${parse.student.schedule.part-time.enabled}")
    private Boolean parseStudentSchedulePartTimeEnabled;

    @Value("${parse.selective.schedule.enabled}")
    private Boolean parseSelectiveScheduleEnabled;

    @Value("${mode}")
    private String parseMode;


    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        boolean failed = false;
        logger.info("[MainJob] Started crawling in {} mode", parseMode);
        try {
            if (parseStudentScheduleFullTimeEnabled) {
                crawlerFT.crawl();
            }
            if (parseStudentSchedulePartTimeEnabled) {
                crawlerPT.crawl();
            }
            if (parseSelectiveScheduleEnabled) {
                selectiveScheduleCrawler.crawl();
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
