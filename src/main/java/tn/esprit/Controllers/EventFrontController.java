package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    @FXML
    private ImageView loooo;

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


                CardController itemController = fxmlLoader.getController();
                itemController.setData(event);

                Insets cardInsets = new Insets(10);
                event_gridPane.setMargin(anchorPane, cardInsets);

                anchorPane.setOnMouseClicked(mouseEvent -> {
                    System.out.println("Card clicked!");

                    if (event instanceof Event) {
                        Event clickedEvent = (Event) event;

                        titree.setText(clickedEvent.getTitre());
                        desc_nada.setText(clickedEvent.getDescription());
                        debutt.setText(clickedEvent.getFormattedDateDebut());
                        finn.setText(clickedEvent.getFormattedDateFin());
                        lieuu.setText(clickedEvent.getLieu());

                        // Charger l'image de l'événement sélectionné dans ImageView
                        if (clickedEvent.getImageURL() != null && !clickedEvent.getImageURL().isEmpty()) {
                            Image image = new Image("file:" + clickedEvent.getImageURL());
                            loooo.setImage(image);
                        } else {
                            // Réinitialiser l'image si aucune image n'est disponible
                            loooo.setImage(null);
                        }
                    }
                });

                if (column == cardsPerRow) {
                    column = 0;
                    row++;
                }

                event_gridPane.add(anchorPane, column++, row);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
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

    @FXML
    void etablissement_btn(ActionEvent event) {

    }

    @FXML
    void event_btn(ActionEvent event) {

    }

    @FXML
    void home_btn(ActionEvent event) {

    }

    @FXML
    void medicament_btn(ActionEvent event) {

    }

    @FXML
    void revervation_btn(ActionEvent event) {

    }

    @FXML
    void sponseur_btn(ActionEvent event) {

    }

    @FXML
    void sujet_btn(ActionEvent event) {

    }
}
