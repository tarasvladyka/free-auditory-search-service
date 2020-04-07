package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;

import java.util.List;

public interface GroupService {
    Group createInInstitute(Group group, Institute institute);

    List<Group> findAllByInstitute(Long instituteId);
}
