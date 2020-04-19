package com.vladyka.lpnu.model;

import com.vladyka.lpnu.model.enums.GroupType;
import com.vladyka.lpnu.model.enums.StudyForm;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGSERIAL")
    private Long id;

    @Column(nullable = false)
    private String abbr;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @Enumerated(STRING)
    @Column(nullable = false)
    private GroupType groupType;

    @Enumerated(STRING)
    @Column(nullable = false)
    private StudyForm studyForm;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public Group setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAbbr() {
        return abbr;
    }

    public Group setAbbr(String abbr) {
        this.abbr = abbr;
        return this;
    }

    public Institute getInstitute() {
        return institute;
    }

    public Group setInstitute(Institute institute) {
        this.institute = institute;
        return this;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public Group setGroupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public Group setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Group setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @PrePersist
    private void prePersist() {
        createdOn = LocalDateTime.now();
    }

}
