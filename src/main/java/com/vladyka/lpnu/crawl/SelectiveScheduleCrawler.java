package com.vladyka.lpnu.crawl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.vladyka.lpnu.model.enums.GroupType.SELECTIVE_DISCIPLINES_GROUP;
import static com.vladyka.lpnu.model.enums.ScheduleType.LESSON_SCHEDULE;
import static com.vladyka.lpnu.model.enums.StudyForm.FULL_TIME;

@Component
@ConditionalOnProperty(value = "parse.selective.schedule.enabled", havingValue = "true")
public class SelectiveScheduleCrawler extends AbstractCrawler {

  public SelectiveScheduleCrawler(@Value("${schedule-page.selective}") String baseUrl) {
    super(baseUrl, SELECTIVE_DISCIPLINES_GROUP, FULL_TIME, LESSON_SCHEDULE);
  }

  public void crawl() {
    parseAndStoreGroups();
    parseAndStoreRegularSchedule();
  }
}


