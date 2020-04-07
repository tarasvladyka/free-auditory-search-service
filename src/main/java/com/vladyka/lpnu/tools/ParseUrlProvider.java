package com.vladyka.lpnu.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ParseUrlProvider {

    @Value("${schedule-page.base-url}")
    private String url;

    private String groupsUrl;

    @PostConstruct
    public void init() {
        groupsUrl = url + "?institutecode_selective=%s";
    }

    public String getBaseUrl() {
        return url;
    }

    public String getGroupsUrl(String instAbbr) {
        return String.format(groupsUrl, instAbbr);
    }
}
