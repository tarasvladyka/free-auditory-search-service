package com.vladyka.lpnu.service;

import com.vladyka.lpnu.dto.ParsedScheduleEntry;

import java.util.List;

public interface ParseService {
    List<String> parseInstitutes(String url);

    List<String> parseGroups(String url);

    List<ParsedScheduleEntry> parseGroupSchedule(String url);
}
