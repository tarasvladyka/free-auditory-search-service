package com.vladyka.lpnu.crawl.student.parttime;

import com.vladyka.lpnu.crawl.ScheduleCrawler;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInstitutesCrawlerPT implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private ParseUrlProvider urlProvider;

    @Override
    public void crawl() {
        logger.info("[Student schedule, Part-time] - Started crawling institutes dropdown");
        List<String> institutes = parseService.parseInstitutes(urlProvider.getBaseUrlPT());
        institutes.forEach(instituteService::createIfNotExists);
        logger.info("[Student schedule, Part-time] - Finished crawling institutes dropdown, total {} institutes", institutes.size());
    }
}
