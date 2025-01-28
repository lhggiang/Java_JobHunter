package vn.hoanggiang.jobhunter.controller;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoanggiang.jobhunter.service.EmailService;
import vn.hoanggiang.jobhunter.service.SubscriberService;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

  private final SubscriberService subscriberService;

  public EmailController(SubscriberService subscriberService) {
    this.subscriberService = subscriberService;
  }

  @GetMapping
  @ApiMessage("Send simple email")
  @Scheduled(cron = "*/10 * * * * *")
  @Transactional
  public String sendSimpleEmail() {
    this.subscriberService.sendSubscribersEmailJobs();
    return "Send email successful";
  }
}

