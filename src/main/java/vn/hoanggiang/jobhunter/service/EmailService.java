package vn.hoanggiang.jobhunter.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import vn.hoanggiang.jobhunter.repository.JobRepository;

@Service
public class EmailService {

  private final JavaMailSender javaMailSender;
  private final SpringTemplateEngine templateEngine;
  private final JobRepository jobRepository;

  public EmailService(JavaMailSender javaMailSender,
                      SpringTemplateEngine templateEngine,
                      JobRepository jobRepository) {
    this.javaMailSender = javaMailSender;
    this.templateEngine = templateEngine;
    this.jobRepository = jobRepository;
  }

  public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
    // Prepare message using a Spring helper
    MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
      message.setTo(to);
      message.setSubject(subject);
      message.setText(content, isHtml);
      this.javaMailSender.send(mimeMessage);
    } catch (MailException | MessagingException e) {
      System.out.println("ERROR SEND EMAIL: " + e);
    }
  }

  @Async
  public void sendEmailFromTemplateSync(
          String to,
          String subject,
          String templateName,
          String username,
          Object value) {

    Context context = new Context();
    context.setVariable("name", username);
    context.setVariable("jobs", value);

    String content = templateEngine.process(templateName, context);
    this.sendEmailSync(to, subject, content, false, true);
  }

}
