package vn.hoanggiang.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoanggiang.jobhunter.domain.Subscriber;
import vn.hoanggiang.jobhunter.service.SubscriberService;
import vn.hoanggiang.jobhunter.util.SecurityUtil;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1/subscribers")
public class SubscriberController {
  private final SubscriberService subscriberService;

  public SubscriberController(SubscriberService subscriberService) {
    this.subscriberService = subscriberService;
  }

  @PostMapping
  @ApiMessage("create a subscriber")
  public ResponseEntity<Subscriber> create(@Valid @RequestBody Subscriber sub) throws IdInvalidException {
    // check email
    boolean isExist = this.subscriberService.isExistsByEmail(sub.getEmail());
    if (isExist == true) {
      throw new IdInvalidException("Email " + sub.getEmail() + " already exists");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(this.subscriberService.createSubscriber(sub));
  }

  @PutMapping
  @ApiMessage("update a subscriber")
  public ResponseEntity<Subscriber> update(@RequestBody Subscriber subsRequest) throws IdInvalidException {
    // check id
    Subscriber subsDB = this.subscriberService.findSubscriberById(subsRequest.getId());
    if (subsDB == null) {
      throw new IdInvalidException("Id " + subsRequest.getId() + " not found");
    }

    return ResponseEntity.ok().body(this.subscriberService.updateSubscriber(subsDB, subsRequest));
  }

  @PostMapping("/subscribers/skills")
  @ApiMessage("get subscriber's skill")
  public ResponseEntity<Subscriber> getSubscribersSkill() throws IdInvalidException {
    String email = SecurityUtil.getCurrentUserLogin().orElse("");
    return ResponseEntity.ok().body(this.subscriberService.findByEmail(email));
  }

}