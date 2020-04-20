package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;

import java.util.List;

public interface GroupService {
    List<Group> createAllInInstitute(List<String> abbrs, String instituteAbbr, GroupType groupType,
                                     StudyForm studyForm);

    List<Group> createAll(List<String> abbrs, GroupType groupType,
                          StudyForm studyForm);

    List<Group> findAll(Long instituteId, GroupType groupType, StudyForm studyForm);

    List<Group> findAll(GroupType groupType, StudyForm studyForm);

    Integer findCount(GroupType groupType, StudyForm studyForm);

    Group find(String groupAbbr, String instAbbr, StudyForm studyForm, GroupType groupType);

    Group find(String groupAbbr, StudyForm studyForm, GroupType groupType);
}
