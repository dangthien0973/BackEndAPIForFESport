package com.example.ApiDoAn.until;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.ApiDoAn.entity.UserEntity;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
public class SendEmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmailWithAttachment(UserEntity user, int verify) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        String toAddress = user.getEmail();
        String fromAddress = "damvannanh@gmail.com";
        String senderName = "Hoàng Văn hiệp";
        String subject = "Please verify your registration";

        String content = "<p> Dear " + user.getUserName() + ",</p>" + "<br>"
                + "\n" +
                "Please get the code below to verify your registration:<br>"
                + "<h3 style=\"color:red ;\">[[code]]</h3>"
                + "Thank you,<br>"
                + "Your company hoanghiep.";
        helper.setFrom(fromAddress, senderName);
        helper.setTo("cubom234@gmail.com");

        helper.setSubject(subject);
        int verifyURL = verify;
        content = content.replace("[[code]]", String.valueOf(verifyURL));

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(content, true);

        // hard coded a file path
        FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        helper.addAttachment("an.png", new ClassPathResource("an.png"));

        javaMailSender.send(msg);

    }
}
