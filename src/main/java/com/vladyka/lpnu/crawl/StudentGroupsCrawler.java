package com.vladyka.lpnu.crawl;

import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import com.vladyka.lpnu.tools.ParseUrlProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StudentGroupsCrawler implements ScheduleCrawler {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;
    @Autowired
    private InstituteService instituteService;
    @Autowired
    private ParseUrlProvider urlProvider;
    @Autowired
    private GroupService groupService;

    @Override
    public void crawl() throws IOException {
        int totalGroups = 0;
        logger.info("Started crawling group info");
        for (Institute institute : instituteService.findAll()) {
            String instAbbr = institute.getAbbr();
            List<String> instituteGroups = parseService.parseGroups(instAbbr);
            groupService.createAllInInstitute(instituteGroups, instAbbr);
            totalGroups += instituteGroups.size();
            logger.info("Parsed and stored info for {} groups in institute {}", instituteGroups.size(), instAbbr);
        }
        logger.info("Finished crawling group info, found {} groups", totalGroups);

    }
}
