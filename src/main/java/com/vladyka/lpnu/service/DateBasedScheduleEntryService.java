package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.DateBasedScheduleEntry;

import java.util.List;

public interface DateBasedScheduleEntryService {
    List<DateBasedScheduleEntry> createAll(List<DateBasedScheduleEntry> entries);
}
