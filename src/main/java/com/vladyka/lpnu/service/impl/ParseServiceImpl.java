package com.vladyka.lpnu.service.impl;

import com.vladyka.lpnu.dto.ParsedScheduleEntry;
import com.vladyka.lpnu.dto.ScheduleContext;
import com.vladyka.lpnu.exception.SchedulePageParseException;
import com.vladyka.lpnu.service.AuditoryServiceImpl;
import com.vladyka.lpnu.service.CampusService;
import com.vladyka.lpnu.service.ParseService;
import com.vladyka.lpnu.tools.ParseHelper;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import static com.vladyka.lpnu.tools.ParseHelper.PARA_DETAILS_PATTERN;

@Service
public class ParseServiceImpl implements ParseService {

    private static final String INSTITUTES_SELECTOR = "#edit-institutecode-selective option";
    private static final String GROUPS_SELECTOR = "#edit-edugrupabr-selective option";

    @Autowired
    private ParseUrlProvider urlProvider;

    @Autowired
    private AuditoryServiceImpl auditoryService;

    @Autowired
    private ParseHelper parseHelper;

    @Autowired
    private CampusService campusService;

    @Override
    public List<String> parseInstitutes() throws IOException {
        List<String> result = new LinkedList<>();
        Elements instituteEntries = Jsoup.connect(urlProvider.getBaseUrl()).get()
                .select(INSTITUTES_SELECTOR);
        instituteEntries.remove(0); //removing first option - ALL

        instituteEntries.forEach(option ->
                result.add(option.attr("value")));

        return result;
    }

    @Override
    public List<String> parseGroups(String instituteAbbr) throws IOException {
        List<String> result = new LinkedList<>();
        Elements options = Jsoup.connect(urlProvider.getGroupsUrl(instituteAbbr)).get()
                .select(GROUPS_SELECTOR);
        options.remove(0); //removing first option - ALL
        options.forEach(element -> result.add(element.attr("value")));
        return result;
    }

    @Override
    public List<ParsedScheduleEntry> parseGroupSchedule(String instituteAbbr, String groupAbbr) throws IOException {
        List<ParsedScheduleEntry> result = new LinkedList<>();
        for (Element dayElement : getDayElements(instituteAbbr, groupAbbr)) {
            ScheduleContext context = new ScheduleContext()
                    .setDay(dayElement.select(".view-grouping-header").text())
                    .setGroupAbbr(groupAbbr)
                    .setInstAbbr(instituteAbbr);
            processDayElement(result, context, dayElement);
        }
        return result;
    }

    private Elements getDayElements(String instAbbr, String groupAbbr) throws IOException {
        return Jsoup.connect(urlProvider.getGroupScheduleUrl(instAbbr, groupAbbr))
                .get()
                .select(".view-content")
                .first()
                .children();
    }

    private void processDayElement(List<ParsedScheduleEntry> result, ScheduleContext context, Element dayElement) {
        for (Element paraElement : getParaElements(dayElement)) {
            if ("h3".equals(paraElement.tagName())) {
                context.setClassNumber(paraElement.text());
            } else {
                processParaElement(result, context, paraElement);
            }
        }
    }

    private Elements getParaElements(Element dayElement) {
        return dayElement
                .select(".view-grouping-content")
                .first()
                .children();
    }

    private void processParaElement(List<ParsedScheduleEntry> result, ScheduleContext context, Element paraElement) {
        for (Element paraSubElement : getParaSubElements(paraElement)) {
            result.add(buildEntry(context, paraSubElement));
        }
    }

    private Elements getParaSubElements(Element paraElement) {
        return paraElement.select(
                "#group_full, " +
                        "#group_znam, " +
                        "#group_chys, " +
                        "#sub_1_full, " +
                        "#sub_2_full," +
                        "#sub_1_chys, " +
                        "#sub_2_chys, " +
                        "#sub_1_znam, " +
                        "#sub_2_znam");
    }

    private ParsedScheduleEntry buildEntry(ScheduleContext context, Element paraSubElement) {
        String details = paraSubElement.select(".group_content")
                .first()
                .html();
        Matcher matcher = PARA_DETAILS_PATTERN.matcher(details);
        if (!matcher.find()) {
            throw new SchedulePageParseException(
                    String.format(
                            "Can not match para details:\n" +
                                    "[%s]\n" +
                                    "to pattern:\n" +
                                    "%s", details, PARA_DETAILS_PATTERN.pattern()));
        }
        return new ParsedScheduleEntry()
                .setDay(context.getDay())
                .setClassNumber(context.getClassNumber())
                .setGroupPart(paraSubElement.id())
                .setOccurrence(paraSubElement.id())
                .setLocation(matcher.group("location"))
                .setClassType(matcher.group("type"))
                .setDescription(matcher.group("desc"))
                .setTeacher(matcher.group("teacher"));
    }

}
