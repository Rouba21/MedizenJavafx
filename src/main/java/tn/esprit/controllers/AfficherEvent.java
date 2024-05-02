package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Event;
import tn.esprit.models.EventListCell;
import tn.esprit.services.EventService;

import java.sql.SQLException;
import java.util.List;

public class AfficherEvent {
    @FXML
    private ListView<Event> liste_event;

    @FXML
    private AnchorPane logout;

    @FXML
    private Button logoutt;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label welcomeid;



    private final EventService eventService = new EventService();


    @FXML
    void initialize() {
        try {
            List<Event> events = eventService.getAllEvents();

            if (events != null && !events.isEmpty()) {
                ObservableList<Event> observableEvents = FXCollections.observableArrayList(events);

                liste_event.setCellFactory(eventListView -> new EventListCell());
                liste_event.setItems(observableEvents);

                // Créer un FilteredList à partir de la liste observable
                FilteredList<Event> filteredList = new FilteredList<>(observableEvents);

                // Ajouter un écouteur sur le champ de recherche pour mettre à jour le filtre
                rechercheField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredList.setPredicate(event -> {
                        if (newValue == null || newValue.isEmpty()) {
                            // Si la recherche est vide, afficher tous les événements
                            return true;
                        }

                        // Filtrer par nom d'événement (insensible à la casse)
                        String lowerCaseFilter = newValue.toLowerCase();
                        return event.getTitre().toLowerCase().contains(lowerCaseFilter);
                    });
                });

                // Appliquer le FilteredList à la ListView
                liste_event.setItems(filteredList);
            } else {
                System.out.println("Aucun événement trouvé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des événements : " + e.getMessage());
        }
    }




    @FXML
    void EventSelect(MouseEvent event) {

    }

    @FXML
    private TextField rechercheField;

    @FXML
    void event_btn(ActionEvent event) {

    }

    @FXML
    void home_btn(ActionEvent event) {

    }

    @FXML
    void sponseur_btn(ActionEvent event) {

    }




}
