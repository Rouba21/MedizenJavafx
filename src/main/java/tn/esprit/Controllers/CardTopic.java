package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.models.Topic;

import java.io.IOException;

public class CardTopic {

    @FXML
    private ImageView topic_image;

    @FXML
    private Label card_topic_title;

    @FXML
    private Label card_topic_contenu;
    @FXML
    private Button ViewButton;

    private Topic topic;

    private static Topic TopicToGetPublication;

    public static Topic getTopicToGetPublication() {
        return TopicToGetPublication;
    }

    public static void setTopicToGetPublication(Topic topicToGetPublication) {
        TopicToGetPublication = topicToGetPublication;
    }

    public void setData(Topic topic) {
        this.topic = topic;
        card_topic_title.setText(topic.getTitre());
        card_topic_contenu.setText(topic.getContenu());
        // Assurez-vous de charger l'image ici si nécessaire
        ViewButton.setOnMouseClicked(event -> {
            setTopicToGetPublication(topic);


            try {
                Parent root = FXMLLoader.load(getClass().getResource("/publicationInterfaces/PublicationsList.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    @FXML
    private void handleViewButtonAction(ActionEvent event) {


        // Ajoutez ici le code pour gérer le clic sur le bouton de visualisation
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
}
