package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import tn.esprit.models.Event;
import tn.esprit.services.EventService;
import tn.esprit.util.sendEmail;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardController {

    private sendEmail mailSender;

    public CardController() {
        this.mailSender = new sendEmail();
    }

    @FXML
    private ImageView event_image;

    @FXML
    private Label event_name;

    private Event event;


    private EventService eventService; // Injecter EventService si nécessaire

    public void setData(Event event) {
        this.event = event;
        event_name.setText(event.getTitre());

        String imageURL = event.getImageURL();
        if (imageURL != null && !imageURL.isEmpty()) {
            try {
                Image image = new Image(imageURL);
                event_image.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Failed to load image", "An error occurred while loading the image.");
            }
        }
    }





    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleParticiperButtonAction(ActionEvent event) {
        if (this.event != null) {
            int userId = getCurrentUserId(); // Récupérer l'ID de l'utilisateur connecté
            try {
                // Appeler la méthode participer de EventService pour enregistrer la participation
                EventService eventService = new EventService();
                eventService.participer(this.event, userId);

                // Envoyer un email de confirmation
                String to = "nadia.bouaicha@esprit.tn"; // Adresse email du destinataire
                String subject = "Confirmation de participation à l'événement";
                String htmlTemplatePath = "src/main/resources/emailVerification.html";
                String codeToInsert = "12345"; // Code ou information à insérer dans l'email
                String clientName = "John Doe"; // Nom du client (facultatif)

                // Appel à la méthode sendMail pour envoyer l'email
                mailSender.sendMail(to, subject, codeToInsert, clientName);

                // Afficher un message de succès
                showSuccessAlert("Succès", "Participation à l'événement confirmée avec succès!");
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Erreur", "Échec de la participation à l'événement : " + e.getMessage());
            }
        }
    }




    private void showSuccessMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int getCurrentUserId() {
        return 1;
    }

    public Object getData() {
        return event;
    }
}
