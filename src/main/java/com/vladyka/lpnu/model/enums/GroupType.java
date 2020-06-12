package com.vladyka.lpnu.model.enums;

public enum GroupType {

  STUDENT_GROUP("Student group"),
  POST_GRADUATE_GROUP("Post-graduate group"),
  SELECTIVE_DISCIPLINES_GROUP("Selective disciplines group");

  private String friendlyName;

  GroupType(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  public String getFriendlyName() {
    return friendlyName;
  }
}
