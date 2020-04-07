package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.repository.GroupRepository;
import com.vladyka.lpnu.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;

    @Override
    public Group createInInstitute(Group group, Institute institute) {
        group.setInstitute(institute);
        return repository.save(group);
    }

    @Override
    public List<Group> findAllByInstitute(Long instituteId) {
        return repository.findAllByInstituteId(instituteId);
    }
}
