package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Event;
import tn.esprit.models.EventListCell;
import tn.esprit.services.EventService;

import java.io.IOException;
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
    @FXML
    private ComboBox<String> dateCombo;
    int sort=0;


    @FXML
    void dateCombo(ActionEvent event) {
        String selectedSort = dateCombo.getValue();

        try {
            List<Event> events = null;

            if (selectedSort != null && selectedSort.equals("Date Croissant")) {
                events = eventService.getEventsByDateAscending();
            } else if (selectedSort != null && selectedSort.equals("Date Decroissant")) {
                events = eventService.getEventsByDateDescending();
            }

            if (events != null && !events.isEmpty()) {
                ObservableList<Event> observableEvents = FXCollections.observableArrayList(events);

                FilteredList<Event> filteredList = new FilteredList<>(observableEvents);

                rechercheField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredList.setPredicate(filteredEvent -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        return filteredEvent.getTitre().toLowerCase().contains(lowerCaseFilter);
                    });
                });

                liste_event.setItems(filteredList);

            } else {
                System.out.println("Aucun événement trouvé pour le tri sélectionné.");
                liste_event.getItems().clear();
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des événements : " + e.getMessage());
        }
    }


    public void refreshEvent() {
        // Effacer la liste actuelle des événements
        liste_event.getItems().clear();

        // Charger à nouveau la liste des événements depuis la base de données
        EventService eventService = new EventService();
        try {
            List<Event> eventList = eventService.getAllEvents();
            // Ajouter les événements à la liste
            liste_event.getItems().addAll(eventList);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'erreur de récupération depuis la base de données
        }
    }



    private final EventService eventService = new EventService();


    @FXML
    void initialize() {

        ObservableList items = dateCombo.getItems();
        items.addAll("Date Croissant","Date Decroissant");
        if(sort==0){
            dateCombo.setValue("Date Croissant");
        }else if (sort==1){
            dateCombo.setValue("Date Decroissant");
        }
        try {


            List<Event> events = eventService.getAllEvents();

            if (events != null && !events.isEmpty()) {
                ObservableList<Event> observableEvents = FXCollections.observableArrayList(events);

                liste_event.setCellFactory(eventListView -> new EventListCell());
                liste_event.setItems(observableEvents);

                // Créer un FilteredList à partir de la liste observable
                FilteredList<Event> filteredList = new FilteredList<>(observableEvents);




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
    void etablissement_btn(ActionEvent event) {

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

        try {
            // Navigation vers une autre vue (AfficherSponseur.fxml)
            // Assurez-vous d'ajuster le chemin d'accès si nécessaire
            Parent root = FXMLLoader.load(getClass().getResource("/Sponseur.fxml"));
            welcomeid.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void sujet_btn(ActionEvent event) {

    }








}
