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
import tn.esprit.services.TopicService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class DetailsTopic {
    @FXML
    private TextField contentText;

    @FXML
    private DatePicker datetopic;

    @FXML
    private TextField titleText;

    @FXML
    private Label titreErrorLabel;

    @FXML
    private Label contenuErrorLabel;

    @FXML
    private Label dateErrorLabel;

    private Topic topic;
    private TopicService topicService;


    public DetailsTopic() {
        this.topicService = new TopicService();
    }

    public void initData(Topic topic) {
        this.topic = topic;
        titleText.setText(topic.getTitre());
        contentText.setText(topic.getContenu());

    }


    // Autres champs et méthodes...

    @FXML
    private void UpdateTopic(ActionEvent event) {
        // Réinitialiser les messages d'erreur
        titreErrorLabel.setText("");
        contenuErrorLabel.setText("");
        dateErrorLabel.setText("");

        // Valider le titre
        String newTitle = titleText.getText();
        if (newTitle.isEmpty() || newTitle.length() < 6) {
            titreErrorLabel.setText("Le titre doit inclure au minimum 6 lettres !");
            return;
        }

        // Valider le contenu
        String newContent = contentText.getText();
        if (newContent.isEmpty()) {
            contenuErrorLabel.setText("Le contenu est requis !");
            return;
        }

        LocalDate localDate = datetopic.getValue();
        if (localDate == null || localDate.isBefore(LocalDate.now())) {
            dateErrorLabel.setText("La date doit être valide !");
            return; // Arrêter l'exécution de la méthode si la validation échoue
        }

        if (topic != null) {
            // Mettre à jour les propriétés du sujet avec les nouvelles valeurs
            topic.setTitre(newTitle);
            topic.setContenu(newContent);
            topic.setDatedecreation(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            try {
                // Appelez la méthode update de votre service de sujet pour mettre à jour le sujet dans la base de données
                topicService.update(topic);
                topicService.update(topic);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTopic.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                // Affichez une alerte de succès si nécessaire
            } catch (SQLException e) {
                // Gestion de l'erreur
                e.printStackTrace(); // Imprimer la trace de l'erreur dans la console
            }
        } else {
            System.out.println("Topic est null");
            // Gérer ce cas d'erreur si nécessaire
        }
    }

    // Autres méthodes de votre contrôleur
    @FXML
    private void DeleteTopic(ActionEvent event) {
        if (topic != null) {
            try {
                // Suppression du topic
                topicService.delete(topic.getId());
                // Logique supplémentaire si nécessaire, comme rafraîchir l'interface
            } catch (SQLException e) {
                // Gestion de l'erreur
                e.printStackTrace(); // Imprimer la trace de l'erreur dans la console
            }
        } else {
            System.out.println("Topic est null");
            // Gérer ce cas d'erreur si nécessaire
        }
    }

    // Autres méthodes de votre contrôleur





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

