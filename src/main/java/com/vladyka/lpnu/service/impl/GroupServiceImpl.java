package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;
import com.vladyka.lpnu.repository.GroupRepository;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.tools.Helper;
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
    private Helper helper;

    @Override
    public List<Group> createAllInInstitute(List<String> abbrs, String instituteAbbr, GroupType groupType,
                                            StudyForm studyForm) {
        Institute institute = instituteService.getByAbbr(instituteAbbr);

        List<String> cleanAbbrs = helper.trimAll(abbrs);
        List<Group> groups = cleanAbbrs.stream()
                .map(abbr -> new Group()
                        .setAbbr(abbr)
                        .setInstitute(institute)
                        .setGroupType(groupType)
                        .setStudyForm(studyForm))
                .filter(
                        group -> find(group.getAbbr(), instituteAbbr, studyForm, groupType) == null)
                .collect(Collectors.toList());
        return repository.saveAll(groups);
    }

    @Override
    public List<Group> createAll(List<String> abbrs, GroupType groupType,
                                 StudyForm studyForm) {

        List<String> cleanAbbrs = helper.trimAll(abbrs);
        List<Group> groups = cleanAbbrs.stream()
                .map(abbr -> new Group()
                        .setAbbr(abbr)
                        .setGroupType(groupType)
                        .setStudyForm(studyForm))
                .filter(
                        group -> find(group.getAbbr(), studyForm, groupType) == null)
                .collect(Collectors.toList());
        return repository.saveAll(groups);
    }

    @Override
    public List<Group> findAll(Long instituteId, GroupType groupType, StudyForm studyForm) {
        return repository.findAllByInstituteIdAndGroupTypeAndStudyForm(instituteId, groupType, studyForm);
    }

    @Override
    public List<Group> findAll(GroupType groupType, StudyForm studyForm) {
        return repository.findAllByGroupTypeAndStudyForm(groupType, studyForm);
    }

    @Override
    public Integer findCount(GroupType groupType, StudyForm studyForm) {
        return repository.findCount(groupType, studyForm);
    }

    @Override
    public Group find(String groupAbbr, String instAbbr, StudyForm studyForm, GroupType groupType) {
        Institute institute = instituteService.getByAbbr(instAbbr);
        return repository.findByAbbrAndInstituteIdAndStudyFormAndGroupType(groupAbbr, institute.getId(), studyForm, groupType);
    }

    @Override
    public Group find(String groupAbbr, StudyForm studyForm, GroupType groupType) {
        return repository.findByAbbrAndStudyFormAndGroupType(groupAbbr, studyForm, groupType);
    }
}
