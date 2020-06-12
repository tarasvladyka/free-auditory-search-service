package com.vladyka.lpnu.tools;

import com.vladyka.lpnu.dto.ClassLocation;
import com.vladyka.lpnu.dto.ParsedScheduleEntry;
import com.vladyka.lpnu.model.*;
import com.vladyka.lpnu.service.AuditoryService;
import com.vladyka.lpnu.service.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import static com.vladyka.lpnu.tools.Helper.CAMPUS_AUDITORY_PATTERN;

@Component
public class ScheduleEntryBuilder {

    @Autowired
    private CampusService campusService;

    @Autowired
    private AuditoryService auditoryService;

    @Autowired
    private Helper helper;

    /**
     * Method may return multiple schedule entries,
     * since schedule might include 2 auditoriums for the same group
     * (for ex. [ел.3лаб Гол. н.к., опт.4лаб Гол. н.к.,  Лабораторна])
     */
    public List<ScheduleEntry> buildRegularOne(ParsedScheduleEntry parsed, Group group) {
        List<ScheduleEntry> entries = new LinkedList<>();
        helper.cleanupParsedScheduleEntry(parsed);

        String classType = parsed.getClassType();
        if (StringUtils.isEmpty(parsed.getClassType())) {
            classType = "UNDEFINED";
        }
        List<ClassLocation> locations = extractLocations(parsed);

        for (ClassLocation location : locations) {
            entries.add(new ScheduleEntry()
                    .setDay(helper.convertToDayOfWeek(parsed.getDay()))
                    .setClassNumber(Integer.valueOf(parsed.getClassNumber()))
                    .setGroupPart(helper.convertToGroupPart(parsed.getGroupPart()))
                    .setOccurrence(helper.convertToOccurrence(parsed.getGroupPart()))
                    .setAuditory(extractAuditory(location))
                    .setDescription(parsed.getDescription())
                    .setTeacher(parsed.getTeacher())
                    .setClassType(helper.convertToClassType(classType))
                    .setGroup(group));
        }
        return entries;
    }

    /**
     * Method may return multiple schedule entries,
     * since schedule might include 2 auditoriums for the same group
     * (for ex. [ел.3лаб Гол. н.к., опт.4лаб Гол. н.к.,  Лабораторна])
     */
    public List<DateBasedScheduleEntry> buildDateBased(ParsedScheduleEntry parsed, Group group) {
        List<DateBasedScheduleEntry> entries = new LinkedList<>();
        helper.cleanupParsedScheduleEntry(parsed);

        String classType = parsed.getClassType();
        if (StringUtils.isEmpty(parsed.getClassType())) {
            classType = "UNDEFINED";
        }
        List<ClassLocation> locations = extractLocations(parsed);

        for (ClassLocation location : locations) {
            entries.add(new DateBasedScheduleEntry()
                    .setScheduledOn(helper.convertToDate(parsed.getDay()))
                    .setClassNumber(Integer.valueOf(parsed.getClassNumber()))
                    .setGroupPart(helper.convertToGroupPart(parsed.getGroupPart()))
                    .setAuditory(extractAuditory(location))
                    .setDescription(parsed.getDescription())
                    .setTeacher(parsed.getTeacher())
                    .setClassType(helper.convertToClassType(classType))
                    .setGroup(group));
        }
        return entries;
    }


    private List<ClassLocation> extractLocations(ParsedScheduleEntry parsed) {
        Matcher campusAuditoryMatcher = CAMPUS_AUDITORY_PATTERN.matcher(parsed.getLocation());
        List<ClassLocation> locations = new LinkedList<>();

        while (campusAuditoryMatcher.find()) {
            locations.add(new ClassLocation()
                    .setAuditoryName(campusAuditoryMatcher.group("auditory"))
                    .setCampusName(campusAuditoryMatcher.group("campus")));
        }
        if (CollectionUtils.isEmpty(locations)) {
            locations.add(new ClassLocation()
                    .setAuditoryName("UNDEFINED")
                    .setCampusName("UNDEFINED"));
        }
        locations = removeDuplicates(locations);
        return locations;
    }

    /**
     * Schedules might include broken data, such as duplicated location
     * for ex: 114 Гол. н.к., 114 Гол. н.к.,  Лекція
     */
    private List<ClassLocation> removeDuplicates(List<ClassLocation> locations) {
        return new LinkedList<>(new HashSet<>(locations));
    }

    private Auditory extractAuditory(ClassLocation classLocation) {
        Campus campus = campusService.getByNameOrElseCreate(
                classLocation.getCampusName());
        return auditoryService.getByNameAndCampusOrElseCreate(
                classLocation.getAuditoryName(),
                campus);
    }
}
