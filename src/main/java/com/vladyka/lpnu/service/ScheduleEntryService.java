package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.ScheduleEntry;

import java.util.List;

public interface ScheduleEntryService {
    List<ScheduleEntry> createAll(List<ScheduleEntry> entries);
}
