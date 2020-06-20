package com.vladyka.lpnu.tools;

import com.vladyka.lpnu.model.enums.WeekType;

public class Utils {

  public static String getWeekTypeReadableName(WeekType weekType) {
    return WeekType.CHYS.equals(weekType) ? "чисельник" : "знаменник";
  }

}
