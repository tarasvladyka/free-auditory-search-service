package com.vladyka.lpnu.crawl.schedule.student.fulltime;

import com.vladyka.lpnu.crawl.schedule.student.ScheduleCrawler;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentScheduleInstitutesCrawlerFT implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private ParseUrlProvider urlProvider;

    @Override
    public void crawl() {
        logger.info("[Student schedule, Full-time] - Started crawling institutes dropDown");
        List<String> institutes = parseService.parseInstitutes(urlProvider.getStudentBaseUrlFT());
        institutes.forEach(instituteService::createIfNotExists);
        logger.info("[Student schedule, Full-time] - Finished crawling institutes dropDown, total {} institutes", institutes.size());
    }
}
