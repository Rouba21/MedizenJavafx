package Medizen.Utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
    public class MailSender {
        private static String SENDER_EMAIL="ines.rouatbi14@gmail.com";
        private static String SENDER_PASSWORD="zctu ankc uzpd adtc";
        private static String HOST="smtp.gmail.com";
        private static String PORT="587";
        public static void SendEmail(String toEmail, String subject, String body) {
            System.out.println(toEmail+" "+subject+" "+body);
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", HOST);
            properties.put("mail.smtp.port", PORT);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                }
            });


            try {
                // Create a default MimeMessage object
                MimeMessage message = new MimeMessage(session);
                // Set From: header field of the header
                message.setFrom(new InternetAddress("ines.rouatbi14@gmail.com"));
                // Set To: header field of the header
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                // Set Subject: header field
                message.setSubject(subject);
                // Now set the actual message
                message.setText(body);

                // Send message
                Transport.send(message);
                System.out.println("Email sent successfully!");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
        }

}
