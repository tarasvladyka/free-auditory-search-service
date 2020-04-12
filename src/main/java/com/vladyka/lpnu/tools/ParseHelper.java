package com.vladyka.lpnu.tools;

import com.vladyka.lpnu.dto.ParsedScheduleEntry;
import com.vladyka.lpnu.model.enums.ClassType;
import com.vladyka.lpnu.model.enums.GroupPart;
import com.vladyka.lpnu.model.enums.Occurrence;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.trim;

@Component
public class ParseHelper {

    /**
     * Test your regex here: regex101.com
     * Teacher - could be empty
     * <br> at the end also could not be present
     * Фізичне виховання - не має викладача, аудиторії, корпусу
     */
    public static final Pattern PARA_DETAILS_PATTERN = Pattern.compile(
            "(?<desc>.+)\\n<br>(?<teacher>.*)?\\n<br>(?<location>.*)(&nbsp;)(?<type>.*)(\\n<br>)?");

    static Pattern CAMPUS_AUDITORY_PATTERN = Pattern.compile("(?<auditory>[\\dАБВГДЕОПТабвгделопт\\\\.]+)\\s(?<campus>[XIV]+|Гол.)\\sн\\.к\\.");

    Occurrence convertToOccurrence(String src) {
        if (src.endsWith("full")) {
            return Occurrence.BOTH;
        } else if (src.endsWith("chys")) {
            return Occurrence.CHYS;
        } else if (src.endsWith("znam")) {
            return Occurrence.ZNAM;
        }
        throw new IllegalArgumentException("Can not extract occurrence out of string: " + src);
    }

    DayOfWeek convertToDayOfWeek(String src) {
        switch (src.toUpperCase()) {
            case "ПН":
                return DayOfWeek.MONDAY;
            case "ВТ":
                return DayOfWeek.TUESDAY;
            case "СР":
                return DayOfWeek.WEDNESDAY;
            case "ЧТ":
                return DayOfWeek.THURSDAY;
            case "ПТ":
                return DayOfWeek.FRIDAY;
            case "СБ":
                return DayOfWeek.SATURDAY;
            case "НД":
                return DayOfWeek.SUNDAY;
        }
        throw new IllegalArgumentException("Can not create day of week from string: " + src);
    }

    GroupPart convertToGroupPart(String paraInfo) {
        if (paraInfo.startsWith("sub_1")) {
            return GroupPart.SUB_GROUP_1;
        } else if (paraInfo.startsWith("sub_2")) {
            return GroupPart.SUB_GROUP_2;
        } else if (paraInfo.startsWith("group")) {
            return GroupPart.FULL_GROUP;
        }
        throw new IllegalArgumentException("Could not extract group part out of string: " + paraInfo);
    }


    ClassType convertToClassType(String classType) {
        if (StringUtils.isEmpty(classType)) {
            throw new IllegalArgumentException("Could not extract class type out of empty string");
        }
        classType = classType.toUpperCase();

        if (classType.contains("ЛЕК")) {
            return ClassType.LEC;
        } else if (classType.contains("ПРАК")) {
            return ClassType.PRAC;
        } else if (classType.contains("ЛАБ")) {
            return ClassType.LAB;
        } else {
            return ClassType.UNDEFINED;
        }
    }

    public List<String> trimAll(List<String> dirtyList) {
        List<String> cleanData = new LinkedList<>();
        for (String name : dirtyList) {
            cleanData.add(trim(name));
        }
        return cleanData;
    }

    void cleanupParsedScheduleEntry(ParsedScheduleEntry parsed) {
        parsed.setClassNumber(trim(parsed.getClassNumber()));
        parsed.setClassType(trim(parsed.getClassType()));
        parsed.setDay((parsed.getDay()));
        parsed.setDescription(trim(parsed.getDescription()));
        parsed.setGroupAbbr(trim(parsed.getGroupAbbr()));
        parsed.setGroupPart(trim(parsed.getGroupPart()));
        parsed.setGroupAbbr(trim(parsed.getGroupAbbr()));
        parsed.setInstAbbr(trim(parsed.getInstAbbr()));
        parsed.setLocation(trim(parsed.getLocation()));
        parsed.setOccurrence(trim(parsed.getOccurrence()));
        parsed.setTeacher(trim(parsed.getTeacher()));
    }
}
