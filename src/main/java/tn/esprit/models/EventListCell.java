package tn.esprit.models;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import tn.esprit.controllers.DetailEvent;
import tn.esprit.controllers.ModifierEvent;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

public class EventListCell extends ListCell<Event> {

    private final ImageView imageView = new ImageView();
    private final Text titreText = new Text();
    private final Text dateDebutText = new Text();
    private final Text dateFinText = new Text();
    private final Text lieuText = new Text();
    private final Text descriptionText = new Text();
    private final Button modifierButton = createStyledIconButton("/img/1.png", "-fx-background-color: #fff;", 40, 40);
    private final Button supprimerButton = createStyledIconButton("/img/delete.jpg", "-fx-background-color: #fff;", 40, 40);
    private final Button detailButton = createStyledIconButton("/img/detail.png", "-fx-background-color: #fff;", 40, 40);

    private static final int IMAGE_WIDTH = 50;
    private static final int IMAGE_HEIGHT = 50;

    public EventListCell() {
        modifierButton.setOnAction(event -> {
            Event selectedEvent = getItem();
            if (selectedEvent != null) {
                navigateToModifierEvent(selectedEvent);
            }
        });

        supprimerButton.setOnAction(event -> {
            Event selectedEvent = getItem();
            if (selectedEvent != null) {
                supprimerEvent(selectedEvent);
            }
        });

        detailButton.setOnAction(event -> {
            Event selectedEvent = getItem();
            if (selectedEvent != null) {
                showEventDetails(selectedEvent);
            }
        });
    }

    @Override
    protected void updateItem(Event event, boolean empty) {
        super.updateItem(event, empty);

        if (empty || event == null) {
            setGraphic(null);
        } else {
            updateVisuals(event);
        }
    }

    private void updateVisuals(Event event) {
        try {
            if (event.getImageURL() != null && !event.getImageURL().isEmpty()) {
                Image image = new Image(event.getImageURL());
                imageView.setImage(image);
                imageView.setFitWidth(IMAGE_WIDTH);
                imageView.setFitHeight(IMAGE_HEIGHT);
            } else {
                imageView.setImage(null);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            imageView.setImage(null);
        }

        titreText.setText("Titre : " + event.getTitre());
        dateDebutText.setText("Date début : " + event.getFormattedDateDebut());
        dateFinText.setText("Date fin : " + event.getFormattedDateFin());
        lieuText.setText("Lieu : " + event.getLieu());
        descriptionText.setText("Description : " + event.getDescription());

        // Organiser les éléments dans un seul HBox
        HBox contentBox = new HBox(10, imageView, titreText, dateDebutText, dateFinText, lieuText, descriptionText, modifierButton, supprimerButton, detailButton);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        setGraphic(contentBox);
    }



    private void supprimerEvent(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                EventService eventService = new EventService();
                eventService.delete(event.getId());
                getListView().getItems().remove(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void navigateToModifierEvent(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();

            ModifierEvent controller = loader.getController();
            controller.initData(event);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showEventDetails(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailEvent.fxml"));
            Parent root = loader.load();

            DetailEvent controller = loader.getController();
            controller.initData(event);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Détails de l'événement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Button createStyledIconButton(String iconFilename, String style, double width, double height) {
        Button button = new Button();
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.setStyle("-fx-font-size: 12px; " + style);

        // Chargement de l'icône depuis le fichier
        InputStream inputStream = getClass().getResourceAsStream(iconFilename);
        if (inputStream != null) {
            Image iconImage = new Image(inputStream);
            ImageView iconView = new ImageView(iconImage);
            iconView.setFitWidth(20); // Taille de l'icône
            iconView.setFitHeight(20);

            button.setGraphic(iconView); // Définir l'icône comme graphique du bouton
        } else {
            System.err.println("Erreur : Impossible de charger l'image " + iconFilename);
        }

        return button;
    }
}