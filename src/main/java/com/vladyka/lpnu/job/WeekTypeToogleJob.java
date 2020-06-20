package com.vladyka.lpnu.job;

import com.vladyka.lpnu.model.enums.WeekType;
import com.vladyka.lpnu.service.PropertyService;
import com.vladyka.lpnu.tools.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.vladyka.lpnu.model.enums.WeekType.CHYS;
import static com.vladyka.lpnu.model.enums.WeekType.ZNAM;

@Component
public class WeekTypeToogleJob {

  private Logger logger = LogManager.getLogger(getClass().getName());

  @Autowired
  private PropertyService propertyService;

  @Scheduled(cron = "${cron.toogle.weektype}")
  public void run() {
    WeekType currentWeekType = propertyService.getWeekType();
    logger.info("Changing week type, current: " + Utils.getWeekTypeReadableName(currentWeekType));

    if (CHYS.equals(currentWeekType)) {
      propertyService.updateWeekType(ZNAM);
      logger.info("Updated week type to: " + Utils.getWeekTypeReadableName(ZNAM));
    } else {
      propertyService.updateWeekType(CHYS);
      logger.info("Updated week type to: " + Utils.getWeekTypeReadableName(CHYS));
    }
  }
}
