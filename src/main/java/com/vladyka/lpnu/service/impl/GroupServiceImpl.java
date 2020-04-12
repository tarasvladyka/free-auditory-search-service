package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.repository.GroupRepository;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.tools.ParseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private ParseHelper parseHelper;

    @Override
    public List<Group> createAllInInstitute(List<String> abbrs, String instituteAbbr) {
        Institute institute = instituteService.getByAbbr(instituteAbbr);

        List<String> cleanAbbrs = parseHelper.trimAll(abbrs);
        List<Group> groups = cleanAbbrs.stream()
                .map(abbr -> new Group()
                        .setAbbr(abbr)
                        .setInstitute(institute))
                .collect(Collectors.toList());
        return repository.saveAll(groups);
    }

    @Override
    public Group create(Group group) {
        return repository.save(group);
    }

    @Override
    public List<Group> findAllByInstitute(Long instituteId) {
        return repository.findAllByInstituteId(instituteId);
    }
}
