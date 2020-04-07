package com.vladyka.lpnu;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import com.vladyka.lpnu.service.impl.ParseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ParserJob {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private ParseServiceImpl parseService;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private GroupService groupService;

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        logger.info("Started parsing");
        try {
            parseAndStoreData();
        } catch (IOException e) {
            logger.error("Error during parsing: ", e);
        }
        logger.info("Finished parsing");
    }

    private void parseAndStoreData() throws IOException {
        List<Institute> institutes = parseService.parseInstitutes();
        logger.info("Parsed info for {} institutes", institutes.size());
        institutes.forEach(instituteService::create);

        for (Institute institute : institutes) {
            List<Group> instituteGroups = parseService.parseGroups(institute);
            logger.info("Parsed info for {} groups, institute {}", instituteGroups.size(), institute.getAbbr());
            instituteGroups.forEach(
                    group -> groupService.createInInstitute(group, institute));
        }
    }
}
