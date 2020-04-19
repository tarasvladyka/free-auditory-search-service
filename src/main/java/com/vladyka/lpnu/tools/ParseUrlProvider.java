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

    // start part-time urls
    public String getBaseUrlPT() {
        return baseUrlPT;
    }

    public String getGroupsUrlPT(String instAbbr) {
        return String.format(baseUrlPT + "?institutecode_selective=%s", instAbbr);
    }

    public String getGroupScheduleUrlPT(String instAbbr, String groupAbbr) {
        return String.format(baseUrlPT + "?institutecode_selective=%s&edugrupabr_selective=%s", instAbbr, groupAbbr);
    }
    // end part-time urls

    // start full-time urls
    public String getBaseUrlFT() {
        return baseUrlFT;
    }

    public String getGroupsUrlFT(String instAbbr) {
        return String.format(baseUrlFT + "?institutecode_selective=%s", instAbbr);
    }

    public String getGroupScheduleUrlFT(String instAbbr, String groupAbbr) {
        return String.format(baseUrlFT + "?institutecode_selective=%s&edugrupabr_selective=%s", instAbbr, groupAbbr);
    }
    // end full-time urls


}
