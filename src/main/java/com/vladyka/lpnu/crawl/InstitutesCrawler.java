package com.vladyka.lpnu.crawl;

import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class InstitutesCrawler implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;

    @Autowired
    private InstituteService instituteService;

    @Override
    public void crawl() throws IOException {
        logger.info("Started crawling institutes info");
        List<String> institutes = parseService.parseInstitutes();
        instituteService.createAll(institutes);
        logger.info("Finished crawling institutes info, found {} institutes", institutes.size());
    }
}
