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


    public void sendEmailWithAttachment(UserEntity user, int verify, String linktoDetail, String Title) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        String toAddress = user.getEmail();
        String fromAddress = "itdangthien0973@gmail.com";
        String senderName = "Nguyễn Đăng Thiện";
        String subject = "Trang tin tức FE SportNews";

        String content = "<h3> Xin chào  " + user.getUserName() + ",</p>" + "<h3>"
                + "\n" +
        		"<h2 style=\\\"black ;\\\">Tin tức hot mới nhất trong ngày :</h2>" + Title + "<br>"+
                "Một số hình ảnh về bài báo"+ "<br>"+
                "  Mời bạn đến link chi tiết:<br>"
                + "<h3 style=\"blue ;\">[[code]]</h3>"
                + "Thank you,<br>"
                + "";
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);

        helper.setSubject(subject);
        int verifyURL = verify;
        content = content.replace("[[code]]", String.valueOf(linktoDetail));

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(content, true);

     

        javaMailSender.send(msg);

    }
}
