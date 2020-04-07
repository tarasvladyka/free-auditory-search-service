package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Institute;

import java.util.List;

public interface InstituteService {
    Institute create(Institute institute);

    List<Institute> findAll();
}
