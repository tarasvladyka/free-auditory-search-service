package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.enums.WeekType;
import com.vladyka.lpnu.repository.PropertyRepository;
import com.vladyka.lpnu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

  @Autowired
  private PropertyRepository repository;

  private final String PROP_WEEK_TYPE = "week.type";

  @Override
  public WeekType getWeekType() {
    return WeekType.valueOf(repository.getByCode(PROP_WEEK_TYPE).getValue());
  }

  @Override
  public void updateWeekType(WeekType newWeekType) {
    repository.updateByCode(PROP_WEEK_TYPE, newWeekType.name());
  }
}
