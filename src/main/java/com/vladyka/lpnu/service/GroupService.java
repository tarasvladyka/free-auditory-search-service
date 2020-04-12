package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Group;

import java.util.List;

public interface GroupService {
    List<Group> createAllInInstitute(List<String> groups, String institute);

    Group create(Group group);

    List<Group> findAllByInstitute(Long instituteId);

    Integer findTotalCount();

    Group findByAbbrAndInstituteAbbr(String groupAbbr, String instAbbr);
}
