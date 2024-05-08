package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Sponseur;
import tn.esprit.models.SponseurListCell;
import tn.esprit.models.getData;
import tn.esprit.services.SponseurService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficheController {

    private final SponseurService sponseurService = new SponseurService();

    @FXML
    private ListView<Sponseur> liste_sponsor;


    @FXML
    private TextField NomTF;

    @FXML
    private Button availableFlowers_importBtn;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField NumeroTF;

    @FXML
    private AnchorPane main_form;

    private Image image;

    @FXML
    private TextField PackTF;

    @FXML
    private ImageView availableFlowers_imageView;
    @FXML
    private ComboBox<String> pack;

    private int filter ;


    @FXML
    void pack(ActionEvent event) {
        if (pack.getValue().equals("Tous")){
            filter=0;
        }else if(pack.getValue().equals("Or")){
            filter=1;
        }else if(pack.getValue().equals("Argent")){
            filter=2;
        }else if (pack.getValue().equals("Bronze")){
            filter=3;;
        }
        this.refreshSponseurs();


    }

    @FXML
    void initialize() throws SQLException {
        ObservableList items = pack.getItems();
        items.addAll("Tous","Or","Argent","Bronze");

        // Charger les sponsors depuis le service
        if(filter==0){
            pack.setValue("Tous");
        }else if (filter==1){
            pack.setValue("Or");
        }else if (filter==2){
            pack.setValue("Argent");
        }else if (filter==3){
            pack.setValue("Bronze");
        }
        List<Sponseur> sponseurs = null;
        if (filter==0){
            sponseurs = sponseurService.afficher();
        }else if (filter==1){
            sponseurs = sponseurService.getSponsorsByPackGold();
        }else if (filter==2){
            sponseurs = sponseurService.getSponsorsByPackArgent();
        }else if (filter==3){
            sponseurs = sponseurService.getSponsorsByPackBronze();
        }

        // Créer une liste observable pour la ListView
        ObservableList<Sponseur> observableList = FXCollections.observableList(sponseurs);

        // Assigner la liste observable à la ListView avec une cellule personnalisée
        liste_sponsor.setItems(observableList);
        liste_sponsor.setCellFactory(param -> new SponseurListCell());

        FilteredList<Sponseur> filteredList = new FilteredList<>(observableList);
        recherche2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    // Si la recherche est vide, afficher tous les événements
                    return true;
                }

                // Filtrer par nom d'événement (insensible à la casse)
                String lowerCaseFilter = newValue.toLowerCase();
                return event.getEmail().toLowerCase().contains(lowerCaseFilter);

            });
        });
        liste_sponsor.setItems(filteredList);

    }

    // Méthode pour récupérer les données des sponseurs depuis la source de données
    private List<Sponseur> getSponseursFromDataSource() throws SQLException {
        // Ici, vous devriez implémenter la logique pour récupérer les sponseurs depuis votre base de données, service, etc.
        // Par exemple :
        SponseurService sponseurService = new SponseurService();
        return sponseurService.getAllSponseurs(); // Supposons que cette méthode renvoie une liste de sponseurs

        // Autres initialisations
    }

    @FXML
    void Supprimer(ActionEvent event) {
        Sponseur selectedSponseur = liste_sponsor.getSelectionModel().getSelectedItem();
        if (selectedSponseur != null) {
            // Confirmer la suppression avec une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce sponseur ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Appeler la méthode delete du service pour supprimer le sponseur
                    sponseurService.delete(selectedSponseur.getId());

                    // Rafraîchir la liste des sponseurs affichés
                   this.refreshSponseurs();

                    // Afficher une alerte de succès
                    showSuccessAlert("Sponseur supprimé avec succès !");
                } catch (SQLException e) {
                    showErrorAlert("Erreur lors de la suppression du sponseur : " + e.getMessage());
                }
            }
        } else {
            showErrorAlert("Veuillez sélectionner un sponseur à supprimer.");
        }
    }

    @FXML
    void SelectedSponseur() {
        Sponseur selectedSponseur = liste_sponsor.getSelectionModel().getSelectedItem();
        if (selectedSponseur != null) {
            // Remplir les champs avec les données du sponseur sélectionné
            NomTF.setText(selectedSponseur.getNom());
            emailTF.setText(selectedSponseur.getEmail());
            NumeroTF.setText(String.valueOf(selectedSponseur.getNumero()));
            PackTF.setText(selectedSponseur.getPack());

            // Charger et afficher l'image du logo du sponseur
            if (selectedSponseur.getLogo() != null && !selectedSponseur.getLogo().isEmpty()) {
                Image image = new Image("file:" + selectedSponseur.getLogo());
                availableFlowers_imageView.setImage(image);
            } else {
                availableFlowers_imageView.setImage(null);
            }
        }
    }



    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        // Méthode pour gérer l'action du bouton Accueil
    }

    @FXML
    void event_btn(ActionEvent event) {
        // Méthode pour gérer l'action du bouton Evénements
    }

    @FXML
    void sponseur_btn(ActionEvent event) {

    }

    @FXML
    void AvailableFlowersInsertImage(ActionEvent event) {
        // Méthode pour gérer l'importation d'une image pour le sponsor
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.jpg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(main_form.getScene().getWindow());
        if (selectedFile != null) {
            getData.path = selectedFile.getAbsolutePath();
            image = new Image(selectedFile.toURI().toString(), 123, 117, false, true);
            availableFlowers_imageView.setImage(image);
        }
    }

    @FXML
    void Naviguer_modif(ActionEvent event) {
        // Méthode pour naviguer vers la modification d'un sponsor
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSponseur.fxml"));
            Parent root = loader.load();

            // Passer le sponseur sélectionné au contrôleur de la vue ModifierSponseur
            ModifierSponseur controller = loader.getController();
            controller.initData(liste_sponsor.getSelectionModel().getSelectedItem());

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de n'importe quel élément de la scène
            Stage stage = (Stage) main_form.getScene().getWindow();

            // Changer la scène de la fenêtre
            stage.setScene(scene);
            stage.setTitle("Modifier Sponseur");
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Erreur lors du chargement de la vue ModifierSponseur : " + e.getMessage());
        }
    }

    public  void refreshSponseurs() {
        try {
            List<Sponseur> sponseurs = null;
            if (filter==0){
                sponseurs = sponseurService.afficher();
            }else if (filter==1){
                sponseurs = sponseurService.getSponsorsByPackGold();
            }else if (filter==2){
                sponseurs = sponseurService.getSponsorsByPackArgent();
            }else if (filter==3){
                sponseurs = sponseurService.getSponsorsByPackBronze();
            }
            // Rafraîchir la liste des sponsors depuis le service
            ObservableList<Sponseur> observableList = FXCollections.observableList(sponseurs);
            liste_sponsor.setItems(observableList);
        } catch (SQLException e) {
            showErrorAlert("Erreur lors du rafraîchissement des sponsors : " + e.getMessage());
        }
    }
    public  void refreshSponseurs2() {
        try {
            List<Sponseur>     sponseurs = sponseurService.afficher();

            ObservableList<Sponseur> observableList = FXCollections.observableList(sponseurs);
            liste_sponsor.setItems(observableList);
            System.out.printf("test delete");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;



    }

    public void initData(Sponseur sponseur) {
        if (sponseur != null) {
            NomTF.setText(sponseur.getNom());
            emailTF.setText(sponseur.getEmail());
            NumeroTF.setText(String.valueOf(sponseur.getNumero()));
            PackTF.setText(sponseur.getPack());
        }
    }

    @FXML
    private TextField recherche2;

    @FXML
    void etablissement_btn(ActionEvent event) {

    }





    @FXML
    void medicament_btn(ActionEvent event) {

    }



    @FXML
    void revervation_btn(ActionEvent event) {

    }



    @FXML
    void sujet_btn(ActionEvent event) {

    }

}
