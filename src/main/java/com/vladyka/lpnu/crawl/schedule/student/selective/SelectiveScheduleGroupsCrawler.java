package com.vladyka.lpnu.crawl.schedule.student.selective;

import com.vladyka.lpnu.crawl.schedule.student.ScheduleCrawler;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vladyka.lpnu.model.enums.GroupType.SELECTIVE_DISCIPLINES_GROUP;
import static com.vladyka.lpnu.model.enums.StudyForm.FULL_TIME;

@Service
public class SelectiveScheduleGroupsCrawler implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;
    @Autowired
    private ParseUrlProvider urlProvider;
    @Autowired
    private GroupService groupService;

    @Override
    public void crawl() {
        logger.info("[Selective schedule] - Started crawling groups dropDown");
        List<String> groups = parseService.parseGroups(urlProvider.getSelectiveScheduleBaseUrl());
        groupService.createAll(groups, SELECTIVE_DISCIPLINES_GROUP, FULL_TIME);
        logger.info("[Selective schedule] - Finished crawling groups dropDown, total = {} groups", groups.size());
    }
}
