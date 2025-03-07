package vn.hoanggiang.jobhunter.service;

import java.nio.charset.StandardCharsets;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
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

  public EmailService(JavaMailSender javaMailSender,
      SpringTemplateEngine templateEngine) {
    this.javaMailSender = javaMailSender;
    this.templateEngine = templateEngine;
  }

  public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
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

  public void sendResetPasswordEmail(String to, String resetLink) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(to);
    helper.setSubject("Reset Your Password");
    helper.setText("<p>Click the link below to reset your password.:</p>" +
            "<a href=\"" + resetLink + "\">Reset password</a>", true);

    javaMailSender.send(message);
  }

}
