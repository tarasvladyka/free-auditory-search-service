package com.vladyka.lpnu.dto;

public class ParsedScheduleEntry {

    private String groupAbbr;

    private String instAbbr;

    private String groupPart;

    private String location;

    private String occurrence;

    private String day;

    private String classNumber;

    private String classType;

    private String description;

    private String teacher;

    public String getGroupAbbr() {
        return groupAbbr;
    }

    public ParsedScheduleEntry setGroupAbbr(String groupAbbr) {
        this.groupAbbr = groupAbbr;
        return this;
    }

    public String getInstAbbr() {
        return instAbbr;
    }

    public ParsedScheduleEntry setInstAbbr(String instAbbr) {
        this.instAbbr = instAbbr;
        return this;
    }

    public String getGroupPart() {
        return groupPart;
    }

    public ParsedScheduleEntry setGroupPart(String groupPart) {
        this.groupPart = groupPart;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ParsedScheduleEntry setLocation(String locationAndClassType) {
        this.location = locationAndClassType;
        return this;
    }

    public String getOccurrence() {
        return occurrence;
    }

    public ParsedScheduleEntry setOccurrence(String occurrence) {
        this.occurrence = occurrence;
        return this;
    }

    public String getDay() {
        return day;
    }

    public ParsedScheduleEntry setDay(String day) {
        this.day = day;
        return this;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public ParsedScheduleEntry setClassNumber(String classNumber) {
        this.classNumber = classNumber;
        return this;
    }

    public String getClassType() {
        return classType;
    }

    public ParsedScheduleEntry setClassType(String classType) {
        this.classType = classType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ParsedScheduleEntry setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTeacher() {
        return teacher;
    }

    public ParsedScheduleEntry setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }
}
