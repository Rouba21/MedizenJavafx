package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import tn.esprit.models.Event;
import tn.esprit.models.Sponseur;
import tn.esprit.models.getData;
import tn.esprit.services.EventService;
import tn.esprit.services.SponseurService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class EventController {

    @FXML
    private ListView<?> liste_event;

    @FXML
    private DatePicker Date_DebutTF1;

    @FXML
    private DatePicker Date_FinTF;

    @FXML
    private TextField DecriptionTF1;

    @FXML
    private TextField LieuTF1;

    @FXML
    private TextField TitreTF1;

    @FXML
    private AnchorPane main_form;

    @FXML
    private ImageView availableFlowers_imageView;

    @FXML
    private ComboBox<String> combobox;

    private Image image;

    private final EventService eventService = new EventService();

    private final ObservableList<String> sponsorNames = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            // Instancier le service pour gérer les sponsors
            SponseurService sponseurService = new SponseurService();

            // Récupérer la liste des sponsors depuis la base de données
            List<Sponseur> sponsors = sponseurService.getAllSponseurs();

            // Afficher le nombre de sponsors récupérés
            System.out.println("Nombre de sponsors récupérés : " + sponsors.size());

            // Afficher les détails de chaque sponsor récupéré
            for (Sponseur sponsor : sponsors) {
                System.out.println("Sponsor récupéré : " + sponsor);
            }

            // Ajouter les noms des sponsors à la liste observable
            ObservableList<String> sponsorNames = FXCollections.observableArrayList();
            for (Sponseur sponsor : sponsors) {
                sponsorNames.add(sponsor.getNom());
            }

            // Définir les noms des sponsors dans le ComboBox
            combobox.setItems(sponsorNames);

        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des sponsors : " + e.getMessage());
        }
    }


    @FXML
    void AjouterEvent(ActionEvent event) {
        String selectedSponseur = combobox.getValue();
        if (selectedSponseur == null) {
            showAlert("Erreur", "Veuillez sélectionner un sponsor !");
            return;
        }

        // Valider et enregistrer l'événement
        if (TitreTF1.getText().isEmpty() ||
                LieuTF1.getText().isEmpty() ||
                DecriptionTF1.getText().isEmpty() ||
                Date_DebutTF1.getValue() == null ||
                Date_FinTF.getValue() == null ||
                image == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        // Valider les dates (Date_DebutTF1 < Date_FinTF)
        LocalDate debutLocalDate = Date_DebutTF1.getValue();
        LocalDate finLocalDate = Date_FinTF.getValue();

        if (debutLocalDate.isAfter(finLocalDate)) {
            showAlert("Erreur", "La date de début doit être antérieure à la date de fin.");
            return;
        }

        try {
            // Créer l'objet Event à ajouter
            java.sql.Date debutSqlDate = java.sql.Date.valueOf(debutLocalDate);
            java.sql.Date finSqlDate = java.sql.Date.valueOf(finLocalDate);
            Event eventToAdd = new Event(
                    TitreTF1.getText().trim(),
                    debutSqlDate,
                    finSqlDate,
                    LieuTF1.getText().trim(),
                    DecriptionTF1.getText().trim(),
                    getData.path
            );

            // Ajouter l'événement et récupérer l'ID ajouté
            int eventId = eventService.add(eventToAdd);

            // Récupérer le sponsor sélectionné
            Sponseur selectedSponsor = getSponsorByName(selectedSponseur);
            if (selectedSponsor == null) {
                showAlert("Erreur", "Aucun sponsor trouvé avec le nom : " + selectedSponseur);
                return;
            }

            // Récupérer l'ID du sponsor
            int sponsorId = selectedSponsor.getId();

            // Ajouter l'association entre l'événement et le sponsor
            eventService.ajouterAssociationEvenementSponseur(eventId, sponsorId);

            showAlert("Succès", "Événement ajouté avec succès !");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout de l'événement : " + e.getMessage());
            e.printStackTrace(); // Imprimer la trace de l'erreur dans la console
        }
    }


    @FXML
    void AvailableFlowersInsertImage(ActionEvent event) {
        FileChooser open = new FileChooser();
        open.setTitle("open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            getData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 123, 117, false, true);
            availableFlowers_imageView.setImage(image);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Sponseur getSponsorByName(String name) throws SQLException {
        SponseurService sponseurService = new SponseurService();
        List<Sponseur> sponsors = sponseurService.getAllSponseurs();
        for (Sponseur sponsor : sponsors) {
            if (sponsor.getNom().equals(name)) {
                return sponsor;
            }
        }
        throw new IllegalArgumentException("Sponsor not found with name: " + name);
    }

    private boolean isValidName(String input) {
        return Pattern.matches("[a-zA-Z ]+", input);
    }

    private boolean isValidNumber(String input) {
        return Pattern.matches("[0-9]+", input);
    }

    @FXML
    void Afficheeventtt(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficheEvent.fxml"));
            main_form.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void event_btn(ActionEvent event) {
        // Ajoutez le traitement pour le bouton event_btn ici
        // Par exemple, vous pouvez ouvrir une nouvelle fenêtre ou effectuer une action spécifique
    }

    @FXML
    void home_btn(ActionEvent event) {
        // Ajoutez le traitement pour le bouton home_btn ici
        // Par exemple, vous pouvez charger la page d'accueil ou effectuer une action spécifique liée à la page d'accueil
    }

    @FXML
    void sponseur_btn(ActionEvent event) {
        try {
            // Navigation vers une autre vue (AfficherSponseur.fxml)
            // Assurez-vous d'ajuster le chemin d'accès si nécessaire
            Parent root = FXMLLoader.load(getClass().getResource("/Sponseur.fxml"));
            DecriptionTF1.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void medicament_btn(ActionEvent event) {
        // Ajoutez le traitement pour le bouton medicament_btn ici
        // Par exemple, vous pouvez effectuer une action spécifique liée aux médicaments ou à la gestion des médicaments
    }

    @FXML
    void reservation_btn(ActionEvent event) {
        // Ajoutez le traitement pour le bouton reservation_btn ici
        // Par exemple, vous pouvez ouvrir une fenêtre pour gérer les réservations ou effectuer une action spécifique liée aux réservations
    }

    @FXML
    void sujet_btn(ActionEvent event) {
        // Ajoutez le traitement pour le bouton sujet_btn ici
        // Par exemple, vous pouvez ouvrir une fenêtre pour gérer les sujets ou effectuer une action spécifique liée aux sujets
    }

    @FXML
    void etablissement_btn(ActionEvent event) {
        // Ajoutez le traitement pour le bouton etablissement_btn ici
        // Par exemple, vous pouvez ouvrir une fenêtre pour gérer les établissements ou effectuer une action spécifique liée aux établissements
    }

    @FXML
    void revervation_btn(ActionEvent event) {

    }


}
