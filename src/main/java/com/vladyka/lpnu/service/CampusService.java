package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Campus;

public interface CampusService {
    Campus getByName(String campusName);

    Campus getByNameOrElseCreate(String campusName);
}
