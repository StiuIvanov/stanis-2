package com.example.demo.schedule;

import com.example.demo.model.service.ParentWithoutChildrenServiceModel;
import com.example.demo.service.ParentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CronScheduler {
    private final ParentService parentService;

    public CronScheduler(ParentService parentService) {
        this.parentService = parentService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CronScheduler.class);

    @Scheduled(cron = "${schedulers.cron}")
    public void cleanDatabase() {

        parentService.cleanDatabaseFromParentsWithoutChildren();

        LOGGER.info("Hello, from cron scheduler at {} I have cleaned up the Database from accounts without children", LocalDateTime.now());
    }

    @Scheduled(cron = "${schedulers.cron1}")
    public void loggerWarningForInactiveUsers() {
        List<ParentWithoutChildrenServiceModel> parentsNamesWithoutChildren = parentService.getParentsNamesWithoutChildren();

        String usernames = parentsNamesWithoutChildren.stream()
                .map(ParentWithoutChildrenServiceModel::getUsername)
                .collect(Collectors.joining(","));

        LOGGER.info("Hello {}, WARNING Users/Parents without children: {}." +
                        " They will be deleted at 00:00 on the 1st of the next month!"
                , LocalDateTime.now(), usernames);
    }

    @Scheduled(cron = "${schedulers.cron2}")
    public void pingMyWebsite() throws IOException {

        HttpURLConnection connection = null;

        URL url = new URL("https://wedding-evi-stani.herokuapp.com/");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        LOGGER.info("PING https://wedding-evi-stani.herokuapp.com/ code " + responseCode + "at:{} ", LocalDateTime.now());
    }
}
