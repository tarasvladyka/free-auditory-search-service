package com.vladyka.lpnu.tools;

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

    public String getStudentBaseUrlPT() {
        return baseUrlPT;
    }

    public String getStudentGroupsUrlPT(String instAbbr) {
        return String.format(baseUrlPT + "?institutecode_selective=%s", instAbbr);
    }

    public String getStudentGroupScheduleUrlPT(String instAbbr, String groupAbbr) {
        return String.format(baseUrlPT + "?institutecode_selective=%s&edugrupabr_selective=%s", instAbbr, groupAbbr);
    }

    public String getStudentBaseUrlFT() {
        return baseUrlFT;
    }

    public String getStudentGroupsUrlFT(String instAbbr) {
        return String.format(baseUrlFT + "?institutecode_selective=%s", instAbbr);
    }

    public String getStudentGroupScheduleUrlFT(String instAbbr, String groupAbbr) {
        return String.format(baseUrlFT + "?institutecode_selective=%s&edugrupabr_selective=%s", instAbbr, groupAbbr);
    }

    public String getSelectiveScheduleBaseUrl() {
        return baseUrlSelective;
    }

    public String getSelectiveGroupScheduleUrl(String groupAbbr) {
        return String.format(baseUrlSelective + "?edugrupabr_selective=%s", groupAbbr);
    }

    public String getPostGraduateBaseUrlFT() {
        return baseUrlPostGraduateFT;
    }

    public String getPostGraduateGroupScheduleUrlFT(String groupAbbr) {
        return String.format(baseUrlPostGraduateFT + "?edugrupabr_selective=%s", groupAbbr);
    }

    public String getPostGraduateBaseUrlPT() {
        return baseUrlPostGraduatePT;
    }

    public String getPostGraduateGroupScheduleUrlPT(String groupAbbr) {
        return String.format(baseUrlPostGraduatePT + "?edugrupabr_selective=%s", groupAbbr);
    }
}
