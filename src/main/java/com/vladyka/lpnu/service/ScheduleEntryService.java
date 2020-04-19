package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.DateBasedScheduleEntry;
import com.vladyka.lpnu.model.ScheduleEntry;

import java.util.List;

public interface ScheduleEntryService {
    List<ScheduleEntry> createAllRegular(List<ScheduleEntry> entries);

    List<DateBasedScheduleEntry> createAllDateBased(List<DateBasedScheduleEntry> entries);
}
