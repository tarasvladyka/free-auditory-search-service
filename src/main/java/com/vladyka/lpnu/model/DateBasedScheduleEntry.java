package com.vladyka.lpnu.model;

import com.vladyka.lpnu.model.enums.ClassType;
import com.vladyka.lpnu.model.enums.GroupPart;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "date_based_schedule_entry")
public class DateBasedScheduleEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGSERIAL")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(STRING)
    @Column(nullable = false)
    private GroupPart groupPart;

    @ManyToOne
    @JoinColumn(name = "auditory_id")
    private Auditory auditory;

    @Column(nullable = false)
    private Integer classNumber;

    @Column(nullable = false)
    private LocalDate scheduledOn;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ClassType classType;

    private String description;

    private String teacher;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public DateBasedScheduleEntry setId(Long id) {
        this.id = id;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public DateBasedScheduleEntry setGroup(Group group) {
        this.group = group;
        return this;
    }

    public GroupPart getGroupPart() {
        return groupPart;
    }

    public DateBasedScheduleEntry setGroupPart(GroupPart groupPart) {
        this.groupPart = groupPart;
        return this;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public DateBasedScheduleEntry setAuditory(Auditory auditory) {
        this.auditory = auditory;
        return this;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public DateBasedScheduleEntry setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
        return this;
    }

    public LocalDate getScheduledOn() {
        return scheduledOn;
    }

    public DateBasedScheduleEntry setScheduledOn(LocalDate scheduledOn) {
        this.scheduledOn = scheduledOn;
        return this;
    }

    public ClassType getClassType() {
        return classType;
    }

    public DateBasedScheduleEntry setClassType(ClassType classType) {
        this.classType = classType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DateBasedScheduleEntry setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTeacher() {
        return teacher;
    }

    public DateBasedScheduleEntry setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public DateBasedScheduleEntry setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @PrePersist
    private void prePersist() {
        createdOn = LocalDateTime.now();
    }
}
