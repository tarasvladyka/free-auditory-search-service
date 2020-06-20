package com.vladyka.lpnu.crawl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.vladyka.lpnu.model.enums.GroupType.STUDENT_GROUP;
import static com.vladyka.lpnu.model.enums.ScheduleType.LESSON_SCHEDULE;
import static com.vladyka.lpnu.model.enums.StudyForm.PART_TIME;

@Component
@ConditionalOnProperty(value = "parse.student.schedule.part-time.enabled", havingValue = "true")
public class StudentPartTimeScheduleCrawler extends AbstractCrawler {

  public StudentPartTimeScheduleCrawler(@Value("${schedule-page.student.part-time}") String baseUrl) {
    super(baseUrl, STUDENT_GROUP, PART_TIME, LESSON_SCHEDULE);
  }

  public void crawl() {
    parseAndStoreGroups();
    parseAndStoreDateBasedSchedule();
  }
}


