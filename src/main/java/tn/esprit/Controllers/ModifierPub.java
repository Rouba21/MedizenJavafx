package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Publication;
import tn.esprit.models.Topic;
import tn.esprit.services.PublicationServiceMontaha;
import tn.esprit.services.TopicServiceMontaha;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class ModifierPub implements Initializable {

    @FXML
    private TextField contenupub;

    @FXML
    private DatePicker datepub;

    @FXML
    private ComboBox<Topic> sujetComboBox;

    @FXML
    private ImageView imagepub;

    @FXML
    private AnchorPane main_form;

    private Publication publication;
    private PublicationServiceMontaha publicationService;
    private String imagePath;

    private final TopicServiceMontaha topicService = new TopicServiceMontaha();

    public void initialize(URL location, ResourceBundle resources) {
        publicationService = new PublicationServiceMontaha();
        // Charger les sujets disponibles
        try {
            List<Topic> topics = topicService.getAll();
            // Afficher les titres des sujets dans une liste déroulante
            sujetComboBox.setItems(FXCollections.observableArrayList(topics));
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions liées à la récupération des sujets
        }
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
        if (publication != null) {
            // Afficher les données de la publication dans les champs
            contenupub.setText(publication.getContenu());
            // Convertir java.sql.Date en java.util.Date
            java.util.Date date = new java.util.Date(publication.getDatedecreation().getTime());
            // Convertir java.util.Date en LocalDate
            datepub.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            // Charger l'image de la publication
            if (publication.getImagePath() != null && !publication.getImagePath().isEmpty()) {
                Image image = new Image(new File(publication.getImagePath()).toURI().toString());
                imagepub.setImage(image);
            }
            // Sélectionner le sujet de la publication
            sujetComboBox.setValue(publication.getTopic());
        }
    }

    @FXML
    void modifierPub(ActionEvent event) {
        if (publication != null) {
            // Mettre à jour les données de la publication avec les valeurs des champs
            publication.setContenu(contenupub.getText());
            publication.setDatedecreation(java.sql.Date.valueOf(datepub.getValue()));

            // Récupérer le sujet sélectionné
            Topic selectedTopic = sujetComboBox.getValue();
            String selectedTopicTitle = "";
            if (selectedTopic != null) {
                // Récupérer le titre du sujet
                selectedTopicTitle = selectedTopic.getTitre();
            }

            // Mettre à jour le titre du sujet de la publication
            publication.getTopic().setTitre(selectedTopicTitle);

            // Mettre à jour l'image de la publication si elle a été modifiée
            if (imagePath != null && !imagePath.isEmpty()) {
                publication.setImage(imagePath);
            }

            // Mettre à jour la publication dans la base de données
            try {
                publicationService.update(publication);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Rediriger vers AffichePub.fxml
            redirectToAffichePub(event);
        }
    }


    private void redirectToAffichePub(ActionEvent event) {
        // Récupérer la publication modifiée en utilisant son ID
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/publicationInterfaces/PublicationsList.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AvailableFlowersInsertImage(ActionEvent event) {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            // Assurez-vous que le fichier sélectionné est une image
            if (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png")) {
                imagePath = file.getAbsolutePath();
                // Chargez l'image et affichez-la dans l'interface utilisateur
                Image image = new Image(file.toURI().toString(), 123, 117, false, true);
                imagepub.setImage(image);
            } else {
                // Affichez un message d'erreur si le fichier n'est pas une image
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a valid image file (.jpg or .png)");
                alert.showAndWait();
            }
        } else {
            // Affichez un message d'erreur si aucun fichier n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No file selected.");
            alert.showAndWait();
        }
    }
}
