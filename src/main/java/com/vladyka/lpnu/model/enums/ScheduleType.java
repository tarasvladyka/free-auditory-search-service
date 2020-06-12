package com.vladyka.lpnu.model.enums;

public enum ScheduleType {
  LESSON_SCHEDULE("Lesson schedule"),
  EXAM_SCHEDULE("Exam schedule");

  private String friendlyName;

  ScheduleType(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  public String getFriendlyName() {
    return friendlyName;
  }
}
