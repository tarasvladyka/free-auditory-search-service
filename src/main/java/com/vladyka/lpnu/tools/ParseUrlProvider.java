package com.vladyka.lpnu.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParseUrlProvider {

    @Value("${schedule-page.base-url}")
    private String url;

    @Value("${schedule-page.institute-url}")
    private String groupsUrl;

    @Value("${schedule-page.group-schedule-url}")
    private String groupScheduleUrl;

    public String getBaseUrl() {
        return url;
    }

    public String getGroupsUrl(String instAbbr) {
        return String.format(groupsUrl, instAbbr);
    }

    public String getGroupScheduleUrl(String instAbbr, String groupAbbr) {
        return String.format(groupScheduleUrl, instAbbr, groupAbbr);
    }
}
