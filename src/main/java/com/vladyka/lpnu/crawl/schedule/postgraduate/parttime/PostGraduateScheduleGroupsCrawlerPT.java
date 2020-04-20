package com.vladyka.lpnu.crawl.schedule.postgraduate.parttime;

import com.vladyka.lpnu.crawl.schedule.student.ScheduleCrawler;
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

import static com.vladyka.lpnu.model.enums.GroupType.POST_GRADUATE_GROUP;
import static com.vladyka.lpnu.model.enums.StudyForm.PART_TIME;

@Service
public class PostGraduateScheduleGroupsCrawlerPT implements ScheduleCrawler {

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
        logger.info("[Postgraduate schedule, Part-time] - Started crawling groups dropDown");
        List<String> groups = parseService.parseGroups(
                parseUrlProvider.getPostGraduateBaseUrlPT());
        groupService.createAll(groups, POST_GRADUATE_GROUP, PART_TIME);
        logger.info("[Postgraduate schedule, Part-time] - Finished crawling groups dropDown, total {} groups", groups.size());
    }
}
