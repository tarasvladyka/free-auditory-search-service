package com.vladyka.lpnu.job;

import com.vladyka.lpnu.crawl.InstitutesCrawler;
import com.vladyka.lpnu.crawl.StudentGroupsCrawler;
import com.vladyka.lpnu.crawl.StudentScheduleCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "mode", havingValue = "CRAWL_ALL")
public class MainJob {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private StudentScheduleCrawler studentScheduleCrawler;
    @Autowired
    private InstitutesCrawler institutesCrawler;
    @Autowired
    private StudentGroupsCrawler groupsCrawler;

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        logger.info("::::::::Started crawling in CRAWL_ALL mode");
        try {
            institutesCrawler.crawl();
            groupsCrawler.crawl();
            studentScheduleCrawler.crawl();
        } catch (Exception e) {
            logger.error("Error during parsing: ", e);
        }
        logger.info("::::::::Finished crawling in CRAWL_ALL mode");
    }
}
