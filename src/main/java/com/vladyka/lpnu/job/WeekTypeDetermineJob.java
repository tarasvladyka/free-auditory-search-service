package com.vladyka.lpnu.job;

import com.vladyka.lpnu.model.enums.WeekType;
import com.vladyka.lpnu.service.PropertyService;
import com.vladyka.lpnu.tools.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WeekTypeDetermineJob {

  private Logger logger = LogManager.getLogger(getClass().getName());

  @Value("${week.type.determination.sample-schedule}")
  private String sampleScheduleUrl;

  @Autowired
  private PropertyService propertyService;

  //  @EventListener(ContextRefreshedEvent.class)
  public void run() {
    logger.info("Determining week type");

    WeekType currentWeekType = null;
    try {
      Document page = Jsoup.connect(sampleScheduleUrl).get();
      boolean isChys = page
              .select("#sub_1_chys, #sub_2_chys, #group_chys")
              .stream()
              .anyMatch(element -> element.hasClass("week_color"));
      boolean isZnam = page
              .select("#sub_1_znam, #sub_2_znam, #group_znam")
              .stream()
              .anyMatch(element -> element.hasClass("week_color"));

      if (isChys && !isZnam) {
        currentWeekType = WeekType.CHYS;
      } else if (isZnam && !isChys) {
        currentWeekType = WeekType.ZNAM;
      }
    } catch (IOException e) {
      logger.error("Unable to get schedule page {}", sampleScheduleUrl);
    }

    if (currentWeekType != null) {
      logger.info("Week type determined: {}", Utils.getWeekTypeReadableName(currentWeekType));
      propertyService.updateWeekType(currentWeekType);
    } else {
      logger.error("Unable to determine week type");
    }
  }
}
