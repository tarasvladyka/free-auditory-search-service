package com.vladyka.lpnu.crawl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.vladyka.lpnu.model.enums.GroupType.POST_GRADUATE_GROUP;
import static com.vladyka.lpnu.model.enums.ScheduleType.LESSON_SCHEDULE;
import static com.vladyka.lpnu.model.enums.StudyForm.PART_TIME;

@Component
@ConditionalOnProperty(value = "parse.post-graduate.schedule.part-time.enabled", havingValue = "true")
public class PostGraduatePartTimeScheduleCrawler extends AbstractCrawler {

  public PostGraduatePartTimeScheduleCrawler(@Value("${schedule-page.post-graduate.part-time}") String baseUrl) {
    super(baseUrl, POST_GRADUATE_GROUP, PART_TIME, LESSON_SCHEDULE);
  }

  public void crawl() {
    parseAndStoreGroups();
    parseAndStoreDateBasedSchedule();
  }
}


