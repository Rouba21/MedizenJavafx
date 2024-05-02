package tn.esprit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EmailTemplateLoader {

    public String loadEmailTemplate() {
        StringBuilder htmlContent = new StringBuilder();

        try {
            // Chargement du fichier depuis le classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("emailVerification.html");

            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    htmlContent.append(line).append("\n");
                }

                reader.close();
            } else {
                System.err.println("Fichier emailVerification.html introuvable !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlContent.toString();
    }

    public static void main(String[] args) {
        EmailTemplateLoader loader = new EmailTemplateLoader();
        String emailTemplate = loader.loadEmailTemplate();

        // Affichage du contenu du modèle d'email
        System.out.println("Contenu du modèle d'email :");
        System.out.println(emailTemplate);
    }
}
