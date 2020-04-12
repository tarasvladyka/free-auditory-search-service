package com.vladyka.lpnu;

import com.vladyka.lpnu.crawl.StudentScheduleCrawler;
import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;
import com.vladyka.lpnu.service.GroupService;
import com.vladyka.lpnu.service.InstituteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ConditionalOnProperty(name = "mode", havingValue = "DEMO")
public class DemoJob {

    private Logger logger = LogManager.getLogger(getClass().getName());

    @Autowired
    private InstituteService instituteService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private StudentScheduleCrawler studentScheduleCrawler;

    @Value("${demo.url}")
    private String demoUrl;
    private static Pattern STUD_SCHEDULE_URL_PATTERN = Pattern.compile(
            "institutecode_selective=(?<inst>.+)&edugrupabr_selective=(?<grp>.+)");

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        logger.info("::::::::Started parsing in DEMO mode");
        try {
            String decodedUrl = URLDecoder.decode(demoUrl, StandardCharsets.UTF_8);
            Matcher matcher = STUD_SCHEDULE_URL_PATTERN.matcher(decodedUrl);
            matcher.find();

            Institute institute = instituteService.create(
                    new Institute()
                            .setAbbr(matcher.group("inst")));
            Group group = groupService.create(
                    new Group()
                            .setAbbr(matcher.group("grp"))
                            .setInstitute(institute));
            logger.info("Parsing schedule for group = {}, institute = {}, url = {}",
                    group.getAbbr(),
                    institute.getAbbr(),
                    decodedUrl);
            studentScheduleCrawler.crawlSinglePage(institute.getAbbr(), group.getAbbr());
        } catch (Exception e) {
            logger.error("Error during parsing: ", e);
        }
        logger.info("::::::::Finished parsing in DEMO mode");
    }
}