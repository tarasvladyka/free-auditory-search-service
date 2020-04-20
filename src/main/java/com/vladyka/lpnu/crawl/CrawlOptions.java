package com.vladyka.lpnu.crawl;

import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;

public class CrawlOptions {

    private GroupType groupType;
    private StudyForm studyForm;

    public GroupType getGroupType() {
        return groupType;
    }

    public CrawlOptions setGroupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public CrawlOptions setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
        return this;
    }
}
