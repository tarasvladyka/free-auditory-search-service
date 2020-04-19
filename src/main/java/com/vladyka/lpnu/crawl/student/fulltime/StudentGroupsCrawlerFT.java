package com.vladyka.lpnu.crawl.student.fulltime;

import com.vladyka.lpnu.crawl.ScheduleCrawler;
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

import static com.vladyka.lpnu.model.enums.GroupType.STUDENT;
import static com.vladyka.lpnu.model.enums.ParseMode.DEMO;
import static com.vladyka.lpnu.model.enums.StudyForm.FULL_TIME;

@Service
public class StudentGroupsCrawlerFT implements ScheduleCrawler {

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
        logger.info("[Student schedule, Full-time] - Started crawling group dropDowns for institutes");
        for (Institute institute : instituteService.findAll()) {
            String instAbbr = institute.getAbbr();
            List<String> instituteGroups = parseService.parseGroups(parseUrlProvider.getGroupsUrlFT(instAbbr));
            groupService.createAll(instituteGroups, instAbbr, STUDENT, FULL_TIME);
            totalGroups += instituteGroups.size();
            logger.info("[Student schedule, Full-time] - Parsed and stored {} groups in institute {}", instituteGroups.size(), instAbbr);
            if (DEMO.name().equalsIgnoreCase(parseMode)) {
                break;
            }
        }
        logger.info("[Student schedule, Full-time] - Finished crawling groups dropDowns, total {} groups", totalGroups);

    }
}
