package tn.esprit.Controllers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Publication;
import tn.esprit.models.Topic;
import tn.esprit.models.TopicStringConverter;
import tn.esprit.models.getData;
import tn.esprit.services.PublicationServiceMontaha;
import tn.esprit.services.TopicServiceMontaha;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterPub implements Initializable {
    @FXML
    private AnchorPane main_form;
    @FXML
    private ImageView imagepub;
    @FXML
    private Label contenuErrorLabel;

    @FXML
    private Label dateErrorLabel;

    @FXML
    private TextField contenupub;

    @FXML
    private DatePicker datepub;
    private Image image;
    @FXML
    private ComboBox<Topic> sujetComboBox;

    private final TopicServiceMontaha topicService = new TopicServiceMontaha();
    private final PublicationServiceMontaha publicationService = new PublicationServiceMontaha();

    public void initialize(URL location, ResourceBundle resources) {
        // Charger les sujets disponibles
        try {
            List<Topic> topics = topicService.getAll();

            // Afficher les titres des sujets dans une liste déroulante
            sujetComboBox.setItems(FXCollections.observableArrayList(topics));
            sujetComboBox.setConverter(new TopicStringConverter());
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions liées à la récupération des sujets
        }
    }



    @FXML
    void AddPub(ActionEvent event) {
        // Réinitialiser les messages d'erreur
        contenuErrorLabel.setText("");
        dateErrorLabel.setText("");
        String imagePath = getData.path;

        // Valider le contenu
        String contenu = contenupub.getText();
        if (contenu.isEmpty()) {
            contenuErrorLabel.setText("Le contenu est requis !");
            return; // Arrêter l'exécution de la méthode si la validation échoue
        }

        // Valider la date
        LocalDate localDate = datepub.getValue();
        if (localDate == null) {
            dateErrorLabel.setText("Veuillez sélectionner une date !");
            return; // Arrêter l'exécution de la méthode si la validation échoue
        }

        // Valider la date par rapport à aujourd'hui
        if (localDate.isBefore(LocalDate.now())) {
            dateErrorLabel.setText("La date doit être valide !");
            return; // Arrêter l'exécution de la méthode si la validation échoue
        }

        // Récupérer l'ID du sujet sélectionné
        Topic selectedTopic = sujetComboBox.getValue();
        if (selectedTopic == null) {
            // Gérer le cas où aucun sujet n'est sélectionné
            // Afficher un message d'erreur ou prendre une autre action appropriée
            return;
        }
        int selectedTopicId = selectedTopic.getId();

        // Créer un nouvel objet Topic avec l'ID sélectionné
        Topic topic = new Topic(selectedTopicId, "", "", null);

        // Si toutes les validations passent, ajouter la publication
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Créer un nouvel objet Publication en utilisant le topic nouvellement créé
        Publication publication = new Publication("", contenu, date, topic);

        // Récupérer le nom du fichier de l'image à partir du chemin complet
        String imageName = new File(imagePath).getName();

        // Ajouter le nom de l'image à la publication
        publication.setImage(imageName);

        // Si un chemin d'image est disponible, ajoutez-le à la publication
        if (imagePath != null && !imagePath.isEmpty()) {
            publication.setImagePath(imagePath);
        }

        // Ajouter la publication
        publicationService.add(publication);

        // Récupérer la publication ajoutée en utilisant son ID
        Publication addedPublication = publicationService.getOne(publication.getId());

        // Afficher la publication ajoutée
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
                getData.path = file.getAbsolutePath();
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
    }}