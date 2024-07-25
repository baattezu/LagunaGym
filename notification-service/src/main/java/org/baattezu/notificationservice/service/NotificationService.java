package org.baattezu.notificationservice.service;

import com.baattezu.shared.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("metrytokill@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
            logger.info("Email sent to {}", toEmail);
        } catch (Exception e) {
            logger.error("Error sending email to {}: {}", toEmail, e.getMessage());
        }
    }

    @KafkaListener(topics = "verification-events", groupId = "notification-group")
    public void handleVerificationEvents(EmailMessage emailMessage) {
        logger.info("Received Message: {}", emailMessage.getMessage());
        sendEmail(emailMessage.getEmail(), "Email Verification", emailMessage.getMessage());
    }

    @KafkaListener(topics = "membership-events", groupId = "notification-group")
    public void handleMembershipEvents(EmailMessage emailMessage) {
        logger.info("Received Message: {}", emailMessage.getMessage());
        sendEmail(emailMessage.getEmail(), "Membership Notification", emailMessage.getMessage());
    }
}

