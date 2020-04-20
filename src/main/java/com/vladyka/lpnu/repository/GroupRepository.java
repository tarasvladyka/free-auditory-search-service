package com.vladyka.lpnu.repository;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByInstituteIdAndGroupTypeAndStudyForm(Long instituteId, GroupType groupType, StudyForm studyForm);

    @Query("SELECT COUNT(*) FROM Group u where u.groupType = :groupType AND u.studyForm = :studyForm")
    Integer findCount(GroupType groupType, StudyForm studyForm);

    Group findByAbbrAndInstituteIdAndStudyFormAndGroupType(String groupAbbr, Long id, StudyForm studyForm, GroupType groupType);

    Group findByAbbrAndStudyFormAndGroupType(String groupAbbr, StudyForm studyForm, GroupType groupType);

    List<Group> findAllByGroupTypeAndStudyForm(GroupType groupType, StudyForm studyForm);
}
