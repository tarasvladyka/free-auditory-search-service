package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Campus;
import com.vladyka.lpnu.repository.CampusRepository;
import com.vladyka.lpnu.service.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampusServiceImpl implements CampusService {

    @Autowired
    private CampusRepository repository;

    @Override
    public Campus getByName(String campusName) {
        return repository.getByName(campusName);
    }

    @Override
    public Campus getByNameOrElseCreate(String campusName) {
        Campus campus = repository.getByName(campusName);
        if (campus == null) {
            campus = repository.save(
                    new Campus()
                            .setName(campusName));
        }
        return campus;
    }
}
