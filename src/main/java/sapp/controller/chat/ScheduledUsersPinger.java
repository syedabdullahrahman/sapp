package sapp.controller.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUsersPinger {

    private static final Logger log = LoggerFactory.getLogger(ScheduledUsersPinger.class);

    @Scheduled(fixedRate = 20000)
    public void reportCurrentTime() {
        System.out.println("SCHEDULED");
    }
}

