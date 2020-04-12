package com.vladyka.lpnu.dto;

public class ScheduleContext {
    private String day;

    private String classNumber;

    private String instAbbr;

    private String groupAbbr;

    public String getDay() {
        return day;
    }

    public ScheduleContext setDay(String day) {
        this.day = day;
        return this;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public ScheduleContext setClassNumber(String classNumber) {
        this.classNumber = classNumber;
        return this;
    }

    public String getInstAbbr() {
        return instAbbr;
    }

    public ScheduleContext setInstAbbr(String instAbbr) {
        this.instAbbr = instAbbr;
        return this;
    }

    public String getGroupAbbr() {
        return groupAbbr;
    }

    public ScheduleContext setGroupAbbr(String groupAbbr) {
        this.groupAbbr = groupAbbr;
        return this;
    }
}
