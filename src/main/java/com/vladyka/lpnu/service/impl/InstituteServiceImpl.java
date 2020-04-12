package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.repository.InstituteRepository;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.tools.ParseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstituteServiceImpl implements InstituteService {

    @Autowired
    private InstituteRepository repository;

    @Autowired
    private ParseHelper parseHelper;

    @Override
    public Institute getByAbbr(String abbr) {
        return repository.getByAbbr(abbr);
    }

    @Override
    public Institute create(Institute institute) {
        return repository.save(institute);
    }

    @Override
    public List<Institute> createAll(List<String> abbrs) {
        List<String> cleanAbbrs = parseHelper.trimAll(abbrs);
        List<Institute> institutes = cleanAbbrs.stream()
                .map(abbr -> new Institute().setAbbr(abbr))
                .collect(Collectors.toList());
        return repository.saveAll(institutes);
    }

    @Override
    public List<Institute> findAll() {
        return repository.findAll();
    }
}
