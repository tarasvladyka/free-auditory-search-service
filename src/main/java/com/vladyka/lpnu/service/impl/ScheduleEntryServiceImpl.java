package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.DateBasedScheduleEntry;
import com.vladyka.lpnu.model.ScheduleEntry;
import com.vladyka.lpnu.repository.DateBasedScheduleEntryRepository;
import com.vladyka.lpnu.repository.ScheduleEntryRepository;
import com.vladyka.lpnu.service.ScheduleEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleEntryServiceImpl implements ScheduleEntryService {

    @Autowired
    private ScheduleEntryRepository regularEntriesRepo;

    @Autowired
    private DateBasedScheduleEntryRepository dateBasedEntriesRepo;

    @Override
    public List<ScheduleEntry> createAllRegular(List<ScheduleEntry> entries) {
        return regularEntriesRepo.saveAll(entries);
    }

    @Override
    public List<DateBasedScheduleEntry> createAllDateBased(List<DateBasedScheduleEntry> entries) {
        return dateBasedEntriesRepo.saveAll(entries);
    }
}
