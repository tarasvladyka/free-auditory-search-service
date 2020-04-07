package com.vladyka.lpnu.model;

import com.vladyka.lpnu.model.enums.ClassType;
import com.vladyka.lpnu.model.enums.GroupPart;
import com.vladyka.lpnu.model.enums.Occurrence;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "schedule_entry")
public class ScheduleEntry {

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

    @Enumerated(STRING)
    @Column(nullable = false)
    private Occurrence occurrence;

    @Enumerated(STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private Integer classNumber;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ClassType classType;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public ScheduleEntry setId(Long id) {
        this.id = id;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public ScheduleEntry setGroup(Group group) {
        this.group = group;
        return this;
    }

    public GroupPart getGroupPart() {
        return groupPart;
    }

    public ScheduleEntry setGroupPart(GroupPart groupPart) {
        this.groupPart = groupPart;
        return this;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public ScheduleEntry setAuditory(Auditory auditory) {
        this.auditory = auditory;
        return this;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public ScheduleEntry setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
        return this;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public ScheduleEntry setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public ScheduleEntry setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
        return this;
    }

    public ClassType getClassType() {
        return classType;
    }

    public ScheduleEntry setClassType(ClassType classType) {
        this.classType = classType;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public ScheduleEntry setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @PrePersist
    private void prePersist() {
        createdOn = LocalDateTime.now();
    }
}
