package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.repository.InstituteRepository;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.tools.ParseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteServiceImpl implements InstituteService {

    @Autowired
    private InstituteRepository repository;

    @Autowired
    private ParseHelper helper;

    @Override
    public Institute getByAbbr(String abbr) {
        return repository.getByAbbr(abbr);
    }

    @Override
    public Institute create(Institute institute) {
        return repository.save(institute);
    }

    @Override
    public Institute createIfNotExists(String instAbbr) {
        Institute institute = getByAbbr(instAbbr);
        if (getByAbbr(instAbbr) == null) {
            institute = create(new Institute().setAbbr(instAbbr));
        }
        return institute;
    }

    @Override
    public List<Institute> findAll() {
        return repository.findAll();
    }
}
