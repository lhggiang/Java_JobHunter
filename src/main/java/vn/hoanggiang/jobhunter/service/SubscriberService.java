package vn.hoanggiang.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import vn.hoanggiang.jobhunter.domain.Job;
import vn.hoanggiang.jobhunter.domain.Skill;
import vn.hoanggiang.jobhunter.domain.Subscriber;
import vn.hoanggiang.jobhunter.domain.response.email.ResEmailJob;
import vn.hoanggiang.jobhunter.repository.JobRepository;
import vn.hoanggiang.jobhunter.repository.SkillRepository;
import vn.hoanggiang.jobhunter.repository.SubscriberRepository;

@Service
@Slf4j
public class SubscriberService {

  private final SubscriberRepository subscriberRepository;
  private final SkillRepository skillRepository;
  private final JobRepository jobRepository;
  private final EmailService emailService;

  public SubscriberService(
      SubscriberRepository subscriberRepository,
      SkillRepository skillRepository,
      JobRepository jobRepository,
      EmailService emailService) {
    this.subscriberRepository = subscriberRepository;
    this.skillRepository = skillRepository;
    this.jobRepository = jobRepository;
    this.emailService = emailService;
  }

  public boolean isExistsByEmail(String email) {
    return this.subscriberRepository.existsByEmail(email);
  }

  public Subscriber createSubscriber(Subscriber subscriber) {
    // check skills
    if (subscriber.getSkills() != null) {
      List<Long> reqSkills = subscriber.getSkills()
          .stream().map(Skill::getId)
          .collect(Collectors.toList());

      List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
      subscriber.setSkills(dbSkills);
    }

    log.info("Create subscriber {} successfully", subscriber.getEmail());
    return this.subscriberRepository.save(subscriber);
  }

  public Subscriber updateSubscriber(Subscriber subsDB, Subscriber subsRequest) {
    // check skills
    if (subsRequest.getSkills() != null) {
      List<Long> reqSkills = subsRequest.getSkills()
          .stream().map(Skill::getId)
          .collect(Collectors.toList());

      List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
      subsDB.setSkills(dbSkills);
    }

    log.info("Update subscriber {} successfully", subsDB.getEmail());
    return this.subscriberRepository.save(subsDB);
  }

  public Subscriber findSubscriberById(long id) {
    Optional<Subscriber> subsOptional = this.subscriberRepository.findById(id);
    log.info("Fetch subscriber with id {} successfully", subsOptional.get().getId());
    return subsOptional.orElse(null);
  }

  public Subscriber findByEmail(String email) {
    log.info("Fetch subscriber with email {} successfully", email);
    return this.subscriberRepository.findByEmail(email);
  }

  public ResEmailJob convertJobToSendEmail(Job job) {
    ResEmailJob res = new ResEmailJob();
    res.setName(job.getName());
    res.setSalary(job.getSalary());
    res.setCompany(new ResEmailJob.CompanyEmail(job.getCompany().getName()));
    List<Skill> skills = job.getSkills();
    List<ResEmailJob.SkillEmail> s = skills.stream().map(skill -> new ResEmailJob.SkillEmail(skill.getName()))
        .collect(Collectors.toList());
    res.setSkills(s);
    return res;
  }

  public void sendSubscribersEmailJobs() {
    List<Subscriber> listSubs = this.subscriberRepository.findAll();
    if (listSubs != null && listSubs.size() > 0) {
      for (Subscriber sub : listSubs) {
        List<Skill> listSkills = sub.getSkills();
        if (listSkills != null && listSkills.size() > 0) {
          List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);
          if (listJobs != null && listJobs.size() > 0) {

            List<ResEmailJob> arr = listJobs.stream().map(
                job -> this.convertJobToSendEmail(job)).collect(Collectors.toList());

            this.emailService.sendEmailFromTemplateSync(
                sub.getEmail(),
                "Hot job opportunities are waiting for you, explore now",
                "job",
                sub.getName(),
                arr);
          }
        }
      }
    }
  }
}