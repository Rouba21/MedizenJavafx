package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.Topic;
import tn.esprit.services.TopicService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherTopic {
    @FXML
    private ListView<Topic> topicListView;

    private final TopicService topicService = new TopicService();

    @FXML
    void initialize() {

        List<Topic> topics = null;

        try {
            topics = topicService.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Topic> observableTopics = FXCollections.observableArrayList(topics);
        topicListView.setItems(observableTopics);
        // Ajouter un listener pour détecter la sélection d'un sujet
        topicListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherDetailsTopic(newValue);
            }
        });


    }


    private void afficherDetailsTopic(Topic topic) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsTopic.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la nouvelle interface
            DetailsTopic controller = loader.getController();

            // Passer les détails du sujet au contrôleur de la nouvelle interface
            controller.initData(topic);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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