package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.repository.InstituteRepository;
import com.vladyka.lpnu.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteServiceImpl implements InstituteService {

    @Autowired
    private InstituteRepository repository;

    @Override
    public Institute create(Institute institute) {
        return repository.save(institute);
    }

    @Override
    public List<Institute> findAll() {
        return repository.findAll();
    }
}
