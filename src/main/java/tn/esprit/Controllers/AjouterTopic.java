package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Topic;
import tn.esprit.services.TopicServiceMontaha;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;

public class AjouterTopic {
    @FXML
    private TextField contenutopic;

    @FXML
    private DatePicker datetopic;

    @FXML
    private TextField titretopic;
    @FXML
    private Label titreErrorLabel;

    @FXML
    private Label contenuErrorLabel;

    @FXML
    private Label dateErrorLabel;
private final TopicServiceMontaha st=new TopicServiceMontaha();
    @FXML
    void AddTopic(ActionEvent event) {
        // Réinitialiser les messages d'erreur
        titreErrorLabel.setText("");
        contenuErrorLabel.setText("");
        dateErrorLabel.setText("");

        // Valider le titre
        String titre = titretopic.getText();
        if (titre.isEmpty() || titre.length() < 6) {
            titreErrorLabel.setText("Le titre doit inclure au minimum 6 lettres !");
            return; // Arrêter l'exécution de la méthode si la validation échoue
        } else if (titre.startsWith(".")) {
            titreErrorLabel.setText("Le titre ne doit pas commencer par un point !");
            return;
        }

        // Valider le contenu
        String contenu = contenutopic.getText();
        if (contenu.isEmpty()) {
            contenuErrorLabel.setText("Le contenu est requis !");
            return; // Arrêter l'exécution de la méthode si la validation échoue
        }

        // Valider la date
        LocalDate localDate = datetopic.getValue();
        if (localDate == null || localDate.isBefore(LocalDate.now())) {
            dateErrorLabel.setText("La date doit être valide !");
            return; // Arrêter l'exécution de la méthode si la validation échoue
        }

        // Si toutes les validations passent, ajouter le topic
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        st.add(new Topic(titre, contenu, date));

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffichageTopic.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherTopic.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void home_btn(ActionEvent actionEvent) {
    }

    public void event_btn(ActionEvent actionEvent) {
    }

    public void sponseur_btn(ActionEvent actionEvent) {
    }

    public void sujet_btn(ActionEvent actionEvent) {
    }

    public void etablissement_btn(ActionEvent actionEvent) {
    }

    public void revervation_btn(ActionEvent actionEvent) {
    }

    public void medicament_btn(ActionEvent actionEvent) {
    }

}