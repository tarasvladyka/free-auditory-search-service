package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.enums.WeekType;

public interface PropertyService {

  WeekType getWeekType();

  void updateWeekType(WeekType newWeekType);
}
