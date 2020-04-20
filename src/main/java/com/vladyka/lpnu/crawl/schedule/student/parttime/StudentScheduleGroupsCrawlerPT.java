package com.vladyka.lpnu.crawl.schedule.student.parttime;

import com.vladyka.lpnu.crawl.schedule.student.ScheduleCrawler;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vladyka.lpnu.model.enums.GroupType.STUDENT_GROUP;
import static com.vladyka.lpnu.model.enums.ParseMode.DEMO;
import static com.vladyka.lpnu.model.enums.StudyForm.PART_TIME;

@Service
public class StudentScheduleGroupsCrawlerPT implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;
    @Autowired
    private InstituteService instituteService;
    @Autowired
    private ParseUrlProvider urlProvider;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ParseUrlProvider parseUrlProvider;

    @Value("${mode}")
    private String parseMode;

    @Override
    public void crawl() {
        int totalGroups = 0;
        logger.info("[Student schedule, Part-time] - Started crawling groups dropDowns");
        for (Institute institute : instituteService.findAll()) {
            String instAbbr = institute.getAbbr();
            List<String> instituteGroups = parseService.parseGroups(urlProvider.getStudentGroupsUrlPT(instAbbr));
            groupService.createAllInInstitute(instituteGroups, instAbbr, STUDENT_GROUP, PART_TIME);
            totalGroups += instituteGroups.size();
            logger.info("[Student schedule, Part-time] - Parsed and stored {} groups in institute {}", instituteGroups.size(), instAbbr);
            if (DEMO.name().equalsIgnoreCase(parseMode)) {
                break;
            }
        }
        logger.info("[Student schedule, Part-time] - Finished crawling groups dropDowns, total = {} groups", totalGroups);

    }
}
