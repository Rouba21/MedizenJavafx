package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tn.esprit.models.Event;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EventFrontController implements Initializable {

    @FXML
    private GridPane event_gridPane;





    @FXML
    private Label titree;
    @FXML
    private Label debut_label;

    @FXML
    private Label debutt;
    @FXML
    private Label desc_nada;
    @FXML
    private Label fin_label;

    @FXML
    private Label finn;



    @FXML
    private Label lieuu;

    private EventService eventService;
    private List<Event> serviceList;
    private Event event;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventService = new EventService();

        int column = 0;
        int row = 1;
        int cardsPerRow = 5;

        try {
            initializeServiceList();
            for (Event event : serviceList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/eventcard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, null, null))); // BorderWidths can be added here if needed

                CardController itemController = fxmlLoader.getController();
                itemController.setData(event);

                // Créer des marges entre les cartes
                Insets cardInsets = new Insets(10); // 10 pixels de marge autour de chaque carte
                event_gridPane.setMargin(anchorPane, cardInsets);

                anchorPane.setOnMouseClicked(mouseEvent -> {
                    System.out.println("Card clicked!");

                    // Assurez-vous que 'event' est de type Event
                    if (event instanceof Event) {
                        // Cast 'event' en tant qu'objet Event
                        Event clickedEvent = (Event) event;

                        // Utilisez les méthodes de l'objet Event pour mettre à jour les libellés dans detail_event
                        titree.setText(clickedEvent.getTitre());
                        desc_nada.setText(clickedEvent.getDescription());
                        debutt.setText(clickedEvent.getFormattedDateDebut());
                        finn.setText(clickedEvent.getFormattedDateFin());
                        lieuu.setText(clickedEvent.getLieu());
                    }
                });

                if (column == cardsPerRow) {
                    column = 0;
                    row++;
                }

                event_gridPane.add(anchorPane, column++, row);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    private void initializeServiceList() throws SQLException {
        this.serviceList = new ArrayList<>(eventService.afficher());
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

    public Event getData() {
        return event;
    }

}
