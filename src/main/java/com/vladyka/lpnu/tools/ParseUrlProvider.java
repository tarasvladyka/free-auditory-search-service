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

    // start part-time urls
    public String getStudentScheduleBaseUrlPT() {
        return baseUrlPT;
    }

    public String getStudentScheduleGroupsUrlPT(String instAbbr) {
        return String.format(baseUrlPT + "?institutecode_selective=%s", instAbbr);
    }

    public String getStudentScheduleGroupScheduleUrlPT(String instAbbr, String groupAbbr) {
        return String.format(baseUrlPT + "?institutecode_selective=%s&edugrupabr_selective=%s", instAbbr, groupAbbr);
    }
    // end part-time urls

    // start full-time urls
    public String getStudentScheduleBaseUrlFT() {
        return baseUrlFT;
    }

    public String getStudentScheduleGroupsUrlFT(String instAbbr) {
        return String.format(baseUrlFT + "?institutecode_selective=%s", instAbbr);
    }

    public String getStudentScheduleGroupScheduleUrlFT(String instAbbr, String groupAbbr) {
        return String.format(baseUrlFT + "?institutecode_selective=%s&edugrupabr_selective=%s", instAbbr, groupAbbr);
    }
    // end full-time urls


    public String getSelectiveScheduleBaseUrl() {
        return baseUrlSelective;
    }

    public String getSelectiveScheduleGroupScheduleUrl(String groupAbbr) {
        return String.format(baseUrlSelective + "?edugrupabr_selective=%s", groupAbbr);
    }
}
