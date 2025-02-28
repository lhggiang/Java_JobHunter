package vn.hoanggiang.jobhunter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.hoanggiang.jobhunter.domain.*;
import vn.hoanggiang.jobhunter.domain.response.email.ResEmailJob;
import vn.hoanggiang.jobhunter.repository.JobRepository;
import vn.hoanggiang.jobhunter.repository.SkillRepository;
import vn.hoanggiang.jobhunter.repository.SubscriberRepository;
import vn.hoanggiang.jobhunter.util.constant.LevelEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationService {
    private final EmailService emailService;
    private final SubscriberService subscriberService;

    public NotificationService(
            EmailService emailService, SubscriberService subscriberService) {
        this.emailService = emailService;
        this.subscriberService = subscriberService;
    }

    @Async
    public void sendJobNotification(User user, JobCreatedEvent event) {
        String message = String.format(
                "New job matching your preferences: %s at %s (%s)",
                event.name(),
                event.companyName(),
                event.location()
        );

        Company company = new Company();
        company.setName(event.companyName());

        Job job = Job.builder()
                .location(event.location())
                .name(event.name())
                .salary(event.salary())
                .level(LevelEnum.fromString(event.level()))
                .company(company)
                .build();


        log.info("Sending notification to {}: {}", user.getEmail(), message);
        emailService.sendEmailFromTemplateSync(user.getEmail(),
                "Hot job opportunities are waiting for you, explore now","job",
                event.name(), job);
    }
}
