package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Auditory;
import com.vladyka.lpnu.model.Campus;
import com.vladyka.lpnu.repository.AuditoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoryServiceImpl implements AuditoryService {

    @Autowired
    private AuditoryRepository repository;

    @Override
    public Auditory getByNameAndCampusOrElseCreate(String auditoryName, Campus campus) {
        Auditory auditory = repository.getByNameAndCampusId(auditoryName, campus.getId());
        if (auditory == null) {
            auditory = repository.save(
                    new Auditory()
                            .setCampus(campus)
                            .setName(auditoryName));
        }
        return auditory;
    }

    @Override
    public Auditory create(Auditory auditory) {
        return repository.save(auditory);
    }
}
