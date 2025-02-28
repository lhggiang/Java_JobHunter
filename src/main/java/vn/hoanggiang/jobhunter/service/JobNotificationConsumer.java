package vn.hoanggiang.jobhunter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.hoanggiang.jobhunter.domain.JobCreatedEvent;
import vn.hoanggiang.jobhunter.domain.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobNotificationConsumer {

    private final NotificationService notificationService;
    private final UserPreferenceService userPreferenceService;

    @KafkaListener(topics = "${jobhunter.kafka.topics.job-created}")
    public void handleJobCreatedEvent(JobCreatedEvent event) {
        log.info("Received job created event: {}", event);

        List<User> subscribers = userPreferenceService.findUsersInterestedInJob(event);
        subscribers.forEach(user ->
                notificationService.sendJobNotification(user, event)
        );
    }
}
