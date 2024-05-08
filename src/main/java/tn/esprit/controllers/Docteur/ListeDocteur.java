package tn.esprit.controllers.Docteur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Docteur;
import tn.esprit.services.DocteurService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListeDocteur implements Initializable {

    public Button ModifierDocteur;
    @FXML
    private Label welcomeid;

    @FXML
    private ListView<Docteur> ListDocteurTT;

    @FXML
    private Pagination paginationFDocteur;

    private ObservableList<Docteur> allDocteurs;

    private final DocteurService docteurService = new DocteurService();

    public static final int ITEMS_PER_PAGE = 2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDocteurs();
        paginationFDocteur.setPageFactory(this::createPage);
        ListDocteurTT.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Docteur item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nomLabel = new Label("Nom: " + item.getNom());
                    Label prenomLabel = new Label("Prenom: " + item.getPrenom());
                    Label mailLabel = new Label("Mail: " + item.getMail());
                    Label adresseLabel = new Label("Adresse: " + item.getAddresse());
                    Label specialiteLabel = new Label("Specialite: " + item.getSpecialite());
                    Label experienceLabel = new Label("Experience: " + item.getExperience());
                    Label mobileLabel = new Label("Mobile: " + item.getMobile());

                    VBox docteurInfoLayout = new VBox(nomLabel, prenomLabel, mailLabel,
                            adresseLabel, specialiteLabel, experienceLabel, mobileLabel);
                    docteurInfoLayout.setSpacing(5);

                    Button editButton = new Button("Edit");
                    editButton.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Docteur/ModifierDocteur.fxml"));
                            Parent root3 = loader.load();
                            ModifierDocteur controller = loader.getController();
                            controller.setDocteur(item);
                            Stage window = (Stage) ModifierDocteur.getScene().getWindow();
                            window.setScene(new Scene(root3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> {
                        boolean confirmed = showConfirmationDialog();
                        if (confirmed) {
                            docteurService.delete(item);
                            loadDocteurs();
                        }
                    });

                    HBox buttonsLayout = new HBox(editButton, deleteButton);
                    buttonsLayout.setSpacing(10);

                    VBox layout = new VBox(docteurInfoLayout, buttonsLayout);
                    layout.setSpacing(10);

                    setGraphic(layout);
                }
            }
        });
    }

    private void loadDocteurs() {
        allDocteurs = FXCollections.observableArrayList(docteurService.getAll());
        ListDocteurTT.setItems(allDocteurs);
        paginationFDocteur.setPageCount(calculatePageCount(allDocteurs.size()));
    }


    private int calculatePageCount(int totalItems) {
        return (totalItems + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        allDocteurs = FXCollections.observableArrayList(docteurService.getAll());
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, allDocteurs.size());
        ListDocteurTT.setItems(FXCollections.observableArrayList(allDocteurs.subList(fromIndex, toIndex)));
        return ListDocteurTT;
    }

    @FXML
    void SupprimerDocteur() {
        Docteur docteurToDelete = ListDocteurTT.getSelectionModel().getSelectedItem();
        if (docteurToDelete != null) {
            boolean confirmed = showConfirmationDialog();
            if (confirmed) {
                docteurService.delete(docteurToDelete);
                loadDocteurs();
            }
        }
    }

    @FXML
    void redirectToModiferDocteur() throws IOException {
        Docteur selectedDocteur = ListDocteurTT.getSelectionModel().getSelectedItem();
        if (selectedDocteur != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Docteur/ModifierDocteur.fxml"));
            Parent root3 = loader.load();
            ModifierDocteur controller = loader.getController();
            controller.setDocteur(selectedDocteur);
            Stage window = (Stage) ListDocteurTT.getScene().getWindow();
            window.setScene(new Scene(root3));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun docteur sélectionné");
            alert.setContentText("Veuillez sélectionner un docteur à modifier.");
            alert.showAndWait();
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce docteur ?");
        alert.setContentText("Cette action est irréversible.");

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeOK;
    }

    @FXML
    void revervation_btn() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Reservation/AjouterReservation.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    @FXML
    void docteur_btn() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Docteur/AjouterDocteur.fxml"));
        welcomeid.getScene().setRoot(root);
    }

    @FXML
    void home_btn() {
        FXMLLoader event = new FXMLLoader(getClass().getResource("/Reservation/Home.fxml"));
    }

    @FXML
    void medicament_btn() {
    }


    @FXML
    void event_btn() {
    }

    @FXML
    void sponseur_btn() {
    }

    @FXML
    void sujet_btn() {
    }

    @FXML
    void etablissement_btn() {
    }
}
