package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Auditory;
import com.vladyka.lpnu.model.Campus;

public interface AuditoryService {
    Auditory getByNameAndCampusOrElseCreate(String auditoryName, Campus campus);

    Auditory create(Auditory auditory);
}
