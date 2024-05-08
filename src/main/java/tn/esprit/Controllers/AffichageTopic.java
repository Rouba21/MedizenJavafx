package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tn.esprit.models.Topic;
import tn.esprit.services.TopicService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AffichageTopic implements Initializable {

    @FXML
    private GridPane topic_gridPane;

    @FXML
    private Label topic_title;

    @FXML
    private Label topic_content;

    @FXML
    private Label topic_date;

    private TopicService topicService;
    private List<Topic> topicList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.topicService = new TopicService();

        int column = 0;
        int row = 1;
        int cardsPerRow = 5;

        try {
            initializeTopicList();
            for (Topic topic : topicList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CardTopic.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, null, null))); // BorderWidths can be added here if needed

                CardTopic itemController = fxmlLoader.getController();
                itemController.setData(topic);

                // Créer des marges entre les cartes
                Insets cardInsets = new Insets(10); // 10 pixels de marge autour de chaque carte
                topic_gridPane.setMargin(anchorPane, cardInsets);

                anchorPane.setOnMouseClicked(mouseEvent -> {
                    System.out.println("Card clicked!");
                    // Assurez-vous de mettre à jour les détails du topic ici si nécessaire
                });

                if (column == cardsPerRow) {
                    column = 0;
                    row++;
                }

                topic_gridPane.add(anchorPane, column++, row);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    private void initializeTopicList() throws SQLException {
        this.topicList = new ArrayList<>(topicService.getAll());
    }

    // Le reste du code reste inchangé


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


}
