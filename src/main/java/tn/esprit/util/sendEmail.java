package tn.esprit.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class sendEmail {

    private static final String EMAIL_TEMPLATE_PATH = "/src/main/ressources/emailVerification.html";

    public void sendMail(String to, String subject, String codeToInsert, String clientName) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String username = "assurancytn@gmail.com";
        String password = "pttwbmzssmunqcdr";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Read HTML template
            try (InputStream inputStream = getClass().getResourceAsStream("/emailVerification.html");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                StringBuilder htmlContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlContent.append(line).append("\n");
                }

                // Replace placeholders in HTML template
                String modifiedHtmlContent = htmlContent.toString()
                        .replace("[verificationCode]", codeToInsert)
                        .replace("[Username]", clientName);

                // Set HTML content as the body of the email
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(modifiedHtmlContent, "text/html; charset=utf-8");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);

                message.setContent(multipart);

                // Send the email
                Transport.send(message);

                System.out.println("Email sent successfully!");
            } catch (IOException | MessagingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
