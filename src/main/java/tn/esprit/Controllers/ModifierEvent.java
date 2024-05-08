package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

public class ModifierEvent {

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
    private ImageView availableFlowers_imageView;

    @FXML
    private Button availableFlowers_importBtn;

    @FXML
    private AnchorPane logout;

    @FXML
    private Button logoutt;

    @FXML
    private Label welcomeid;

    private Event event;
    private String imageURL; // URL de l'image



    public void initData(Event event) {
        this.event = event;

        TitreTF1.setText(event.getTitre());
        LieuTF1.setText(event.getLieu());
        DecriptionTF1.setText(event.getDescription());

        // Convertir java.util.Date en java.time.LocalDate pour les DatePicker
        LocalDate dateDebut = event.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFin = event.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date_DebutTF1.setValue(dateDebut);
        Date_FinTF.setValue(dateFin);

        // Charger l'image si l'URL est valide
        if (isValidImageURL(imageURL)) {
            try {
                Image image = new Image(imageURL);
                availableFlowers_imageView.setImage(image);
            } catch (Exception e) {
                // Gérer l'exception en affichant un message d'erreur
                showErrorAlert("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
            // Gérer le cas où l'URL de l'image est invalide ou manquante
            showErrorAlert("L'URL de l'image est invalide ou la ressource est introuvable : " + imageURL);
        }
    }
    // Méthode de validation d'URL
    private boolean isValidImageURL(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void redirectToAfficherEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSponseur.fxml"));
            Parent root = loader.load();
            AfficherEvent controller = loader.getController();
            controller.refreshEvent(); // Actualiser la liste des sponsors

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) LieuTF1.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showErrorAlert("Erreur lors du chargement de AfficherSponseur.fxml : " + e.getMessage());
        }
    }








    @FXML
    void updateEvent() {
        // Récupérer les données de l'événement à partir de l'interface utilisateur
        String titre = TitreTF1.getText().trim();
        String lieu = LieuTF1.getText().trim();
        String description = DecriptionTF1.getText().trim();
        LocalDate dateDebut = Date_DebutTF1.getValue();
        LocalDate dateFin = Date_FinTF.getValue();

        // Vérifier la validité des champs
        if (!isValidEvent(titre, lieu, description, dateDebut, dateFin, imageURL)) {
            return; // Si les données ne sont pas valides, quitter la méthode
        }



        // Créer ou mettre à jour l'événement avec l'URL de l'image
        Event updatedEvent = new Event(
                event.getId(), // Utiliser l'ID de l'événement existant
                titre,
                dateDebut,
                dateFin,
                lieu,
                description,
                imageURL
        );

        // Confirmer la mise à jour avec une boîte de dialogue
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir mettre à jour cet événement ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Appeler le service pour mettre à jour l'événement dans la base de données
                EventService eventService = new EventService(); // Instancier le service
                eventService.update(updatedEvent); // Passer l'événement à mettre à jour

                showSuccessAlert("Événement mis à jour avec succès !");
                redirectToAfficherEvent();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isValidEvent(String titre, String lieu, String description, LocalDate dateDebut, LocalDate dateFin, String imageURL) {
        // Vérifier la validité des champs (titre, lieu, description, date)
        // Vous pouvez implémenter des vérifications spécifiques ici
        // Par exemple, vérifier si les champs contiennent uniquement des lettres et des espaces, etc.
        return isValidName(titre) && isValidName(lieu) && isValidName(description) && isDateValid(dateDebut, dateFin) && isValidImageURL(imageURL);
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    private boolean isDateValid(LocalDate dateDebut, LocalDate dateFin) {
        return dateDebut != null && dateFin != null && dateDebut.isBefore(dateFin);
    }







    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    @FXML
    void AvailableFlowersInsertImage(ActionEvent event) {

    }

    @FXML
    void etablissement_btn(ActionEvent event) {

    }

    @FXML
    void event_btn(ActionEvent event) {

    }

}
