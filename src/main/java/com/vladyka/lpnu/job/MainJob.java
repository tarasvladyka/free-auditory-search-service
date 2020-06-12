package com.vladyka.lpnu.job;

import com.vladyka.lpnu.crawl.Crawler;
import com.vladyka.lpnu.exception.SchedulePageParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainJob {

  private Logger logger = LogManager.getLogger(getClass().getName());

  @Value("${mode}")
  protected String parseMode;

  @Autowired
  private List<Crawler> crawlers;

  @EventListener(ContextRefreshedEvent.class)
  public void run() {
    logger.info("--------------------------------------------");
    logger.info("Started crawling, parse mode = {}", parseMode);
    logger.info("--------------------------------------------");

    boolean overallSuccess = true;

    for (Crawler crawler : crawlers) {
      logger.info(crawler.getLogPrefix() + " - Started", parseMode);
      boolean success = true;

      try {
        crawler.crawl();
      } catch (SchedulePageParseException e) {
        logger.error(String.format(
                "Exception occurred during parsing page at url:\n\n" +
                        "%s\n\n" +
                        "%s\n",
                e.getUrl(),
                e.getMessage()));
        success = false;
        overallSuccess = false;

      } catch (Exception e) {
        logger.error("Unexpected exception occurred: ", e);
        success = false;
        overallSuccess = false;
      }

      logger.info(crawler.getLogPrefix() + " - Finished, success = {}", success);
      logger.info("--------------------------------------------");
    }


    logger.info("Finished crawling, overall success = {}", overallSuccess);
    logger.info("--------------------------------------------");
  }
}
