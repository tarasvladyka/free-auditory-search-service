package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.ScheduleEntry;
import com.vladyka.lpnu.repository.ScheduleEntryRepository;
import com.vladyka.lpnu.service.ScheduleEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleEntryServiceImpl implements ScheduleEntryService {

    @Autowired
    private ScheduleEntryRepository repository;

    @Override
    public List<ScheduleEntry> createAll(List<ScheduleEntry> entries) {
        return repository.saveAll(entries);
    }
}
