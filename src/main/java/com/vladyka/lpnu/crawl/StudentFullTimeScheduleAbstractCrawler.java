package com.vladyka.lpnu.crawl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.vladyka.lpnu.model.enums.GroupType.STUDENT_GROUP;
import static com.vladyka.lpnu.model.enums.ScheduleType.LESSON_SCHEDULE;
import static com.vladyka.lpnu.model.enums.StudyForm.FULL_TIME;

@Component
@ConditionalOnProperty(value = "parse.student.schedule.full-time.enabled", havingValue = "true")
public class StudentFullTimeScheduleAbstractCrawler extends AbstractCrawler {

  public StudentFullTimeScheduleAbstractCrawler(@Value("${schedule-page.student.full-time}") String baseUrl) {
    super(baseUrl, STUDENT_GROUP, FULL_TIME, LESSON_SCHEDULE);
  }

  public void crawl() {

    parseAndStoreInstitutes();
    parseAndStoreGroupsByInstitutes();
    parseAndStoreRegularSchedule();
  }
}


