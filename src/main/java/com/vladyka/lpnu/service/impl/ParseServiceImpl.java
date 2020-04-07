package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.service.ParseService;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParseServiceImpl implements ParseService {

    private static final String INSTITUTES_SELECTOR = "#edit-institutecode-selective option";
    private static final String GROUPS_SELECTOR = "#edit-edugrupabr-selective option";

    @Autowired
    private ParseUrlProvider urlProvider;

    @Override
    public List<Institute> parseInstitutes() throws IOException {
        List<Institute> result = new LinkedList<>();
        Elements instituteEntries = Jsoup.connect(urlProvider.getBaseUrl()).get()
                .select(INSTITUTES_SELECTOR);
        instituteEntries.remove(0); //removing first option - ALL

        instituteEntries.forEach(option ->
                result.add(
                        new Institute()
                                .setAbbr(option.attr("value"))));

        return result;
    }

    @Override
    public List<Group> parseGroups(Institute institute) throws IOException {
        List<Group> result = new LinkedList<>();
        Elements options = Jsoup.connect(urlProvider.getGroupsUrl(institute.getAbbr())).get()
                .select(GROUPS_SELECTOR);
        options.remove(0); //removing first option - ALL
        options.forEach(element -> result.add(
                new Group()
                        .setAbbr(element.attr("value"))));
        return result;
    }
}
