package tn.esprit;

import tn.esprit.util.sendEmail;

public class Main {

    public static void main(String[] args) {
        sendEmail mailSender = new sendEmail();

        String to = "nadia.bouaicha@esprit.tn";
        String subject = "Confirmation Email";
        String codeToInsert = "12345";
        String clientName = "John Doe";

        mailSender.sendMail(to, subject, codeToInsert, clientName);
    }
}
