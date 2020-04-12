package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Institute;

import java.util.List;

public interface InstituteService {
    Institute create(Institute institute);

    Institute getByAbbr(String abbr);

    List<Institute> createAll(List<String> abbrs);

    List<Institute> findAll();
}
