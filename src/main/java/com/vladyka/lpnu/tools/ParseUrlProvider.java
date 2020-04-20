package com.vladyka.lpnu.tools;

import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * FT - full-time schedule(очна форма)
 * PT - part-time schedule(заочна форма)
 */
@Component
public class ParseUrlProvider {

    @Value("${schedule-page.student.full-time}")
    private String baseUrlFT;
    @Value("${schedule-page.student.part-time}")
    private String baseUrlPT;
    @Value("${schedule-page.selective}")
    private String baseUrlSelective;
    @Value("${schedule-page.post-graduate.full-time}")
    private String baseUrlPostGraduateFT;
    @Value("${schedule-page.post-graduate.part-time}")
    private String baseUrlPostGraduatePT;

    public String getBaseUrl(GroupType groupType, StudyForm studyForm) {
        if (GroupType.STUDENT_GROUP.equals(groupType) && StudyForm.FULL_TIME.equals(studyForm)) {
            return baseUrlFT;
        } else if (GroupType.STUDENT_GROUP.equals(groupType) && StudyForm.PART_TIME.equals(studyForm)) {
            return baseUrlPT;
        } else if (GroupType.POST_GRADUATE_GROUP.equals(groupType) && StudyForm.PART_TIME.equals(studyForm)) {
            return baseUrlPostGraduatePT;
        } else if (GroupType.POST_GRADUATE_GROUP.equals(groupType) && StudyForm.FULL_TIME.equals(studyForm)) {
            return baseUrlPostGraduateFT;
        } else if (GroupType.SELECTIVE_DISCIPLINES_GROUP.equals(groupType) && StudyForm.FULL_TIME.equals(studyForm)) {
            return baseUrlSelective;
        } else {
            throw new IllegalArgumentException("Specified group type and study form is not supported");
        }
    }

    public String getGroupsUrl(GroupType groupType, StudyForm studyForm, String instAbbr) {
        if (GroupType.STUDENT_GROUP.equals(groupType)) {
            return String.format(getBaseUrl(groupType, studyForm) + "?institutecode_selective=%s", instAbbr);
        } else {
            throw new IllegalArgumentException("Groups url could not build for these params");
        }
    }

    public String getGroupScheduleUrl(GroupType groupType, StudyForm studyForm, String groupAbbr) {
        return String.format(getBaseUrl(groupType, studyForm) + "?institutecode_selective=All&edugrupabr_selective=%s", groupAbbr);
    }
}
