package com.vladyka.lpnu.crawl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.vladyka.lpnu.model.enums.GroupType.POST_GRADUATE_GROUP;
import static com.vladyka.lpnu.model.enums.ScheduleType.EXAM_SCHEDULE;
import static com.vladyka.lpnu.model.enums.StudyForm.FULL_TIME;

@Component
@ConditionalOnProperty(value = "parse.post-graduate.exam.schedule.full-time.enabled", havingValue = "true")
public class PostGraduateExamScheduleCrawler extends Crawler {

  public PostGraduateExamScheduleCrawler(@Value("${exam.schedule-page.post-graduate.full-time}") String baseUrl) {
    super(baseUrl, POST_GRADUATE_GROUP, FULL_TIME, EXAM_SCHEDULE);
  }

  public void crawl() {
    parseAndStoreGroups();
    parseAndStoreDateBasedSchedule();
  }
}


