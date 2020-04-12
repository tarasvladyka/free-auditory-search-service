package com.vladyka.lpnu.service;

import com.vladyka.lpnu.dto.ParsedScheduleEntry;

import java.io.IOException;
import java.util.List;

public interface ParseService {
    List<String> parseInstitutes() throws IOException;

    List<String> parseGroups(String instituteAbbr) throws IOException;

    List<ParsedScheduleEntry> parseGroupSchedule(String instituteAbbr, String groupAbbr) throws IOException;
}
