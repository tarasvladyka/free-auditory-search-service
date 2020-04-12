package com.vladyka.lpnu.dto;

import java.util.Objects;

public class ClassLocation {

    private String campusName;

    private String auditoryName;

    public String getCampusName() {
        return campusName;
    }

    public ClassLocation setCampusName(String campusName) {
        this.campusName = campusName;
        return this;
    }

    public String getAuditoryName() {
        return auditoryName;
    }

    public ClassLocation setAuditoryName(String auditoryName) {
        this.auditoryName = auditoryName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassLocation that = (ClassLocation) o;
        return Objects.equals(campusName, that.campusName) &&
                Objects.equals(auditoryName, that.auditoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campusName, auditoryName);
    }
}
