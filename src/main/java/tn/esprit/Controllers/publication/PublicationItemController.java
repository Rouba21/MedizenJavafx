package tn.esprit.Controllers.publication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.Controllers.AffichePub;
import tn.esprit.Controllers.ModifierPub;
import tn.esprit.models.Publication;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.LikeService;
import tn.esprit.services.PublicationService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PublicationItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text pubItemContenu;

    @FXML
    private Text pubItemDate;

    @FXML
    private Label pubItemDeleteBtn;

    @FXML
    private ImageView pubItemDeleteBtnImg;

    @FXML
    private ImageView pubItemImg;

    @FXML
    private Text pubItemProp;

    @FXML
    private Label pubItemShowBtn;

    @FXML
    private ImageView pubItemShowBtnImg;

    @FXML
    private Text pubItemTitre;

    @FXML
    private Label pubItemUpdateBtn;

    @FXML
    private ImageView pubItemUpdateBtnImg;
    private static Publication publicationToGetLikes;

    public static Publication getPublicationToGetLikes() {
        return publicationToGetLikes;
    }

    public static void setPublicationToGetLikes(Publication publicationToGetLikes) {
        PublicationItemController.publicationToGetLikes = publicationToGetLikes;
    }

    public void setData(Publication p){
        String imageName = p.getImage();
        if (imageName != null && !imageName.isEmpty()) {
            // Construire le chemin d'accès complet à partir du nom de fichier (vous devez spécifier le chemin de base)
            String imagePath = "/img/" + imageName; // Assurez-vous que le chemin est correct, et remplacez "/img/" par votre chemin réel
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            if (inputStream != null) {
                try {
                    Image image = new Image(inputStream);
                    pubItemImg.setImage(image);
                } catch (Exception e) {
                    // Gestion des erreurs
                    e.printStackTrace();
                    System.err.println("Erreur lors du chargement de l'image : " + imagePath);
                }
            } else {
                System.err.println("L'image n'existe pas : " + imagePath);
            }
        } else {
            // Si aucune image n'est associée à la publication, vous pouvez afficher une image par défaut ou laisser vide
            // Par exemple : imagepub.setImage(null);
        }
        pubItemDate.setText(String.valueOf(p.getDatedecreation()));
        pubItemContenu.setText(String.valueOf(p.getContenu()));
        pubItemShowBtn.setOnMouseClicked(event -> {
            PublicationItemController.setPublicationToGetLikes(p);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publicationInterfaces/PublicationsList.fxml"));
            try {
                Parent root = loader.load();
                HBox contentArea = (HBox) ((Node) event.getSource()).getScene().lookup("#updateUserModel");
                contentArea.setVisible(true);
                Text titreLabel = (Text) ((Node) event.getSource()).getScene().lookup("#WindowTitle");
                titreLabel.setText("Details");

                VBox DevisModelContent = (VBox) ((Node) event.getSource()).getScene().lookup("#updateUserModelContent");
                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/AffichePub.fxml"));
                VBox DevisItem = null;
                DevisItem = fxmlLoader1.load();
                AffichePub up = fxmlLoader1.getController();
                up.afficherPublication(p);



                DevisModelContent.getChildren().clear();
                DevisModelContent.getChildren().add(DevisItem);
            } catch (IOException e) {
                e.printStackTrace();
            }





        });
        pubItemDeleteBtn.setOnMouseClicked(event -> {

            PublicationService publicationService=new PublicationService();

            CommentaireService commentaireService=new CommentaireService();
            LikeService likeService = new LikeService();
            try {
                likeService.deleteByPublicationAndUser(p.getId(),1);
                commentaireService.delete2(p.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {

                publicationService.delete(p.getId());


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/publicationInterfaces/PublicationsList.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
        pubItemUpdateBtn.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publicationInterfaces/PublicationsList.fxml"));
            try {
                Parent root = loader.load();
                HBox contentArea = (HBox) ((Node) event.getSource()).getScene().lookup("#updateUserModel");
                contentArea.setVisible(true);
                Text titreLabel = (Text) ((Node) event.getSource()).getScene().lookup("#WindowTitle");
                titreLabel.setText("Mise a jour Publication");

                VBox DevisModelContent = (VBox) ((Node) event.getSource()).getScene().lookup("#updateUserModelContent");
                FXMLLoader fxmlLoader1 = new FXMLLoader();
                fxmlLoader1.setLocation(getClass().getResource("/ModifierPub.fxml"));
                VBox DevisItem = null;
                DevisItem = fxmlLoader1.load();
                ModifierPub up = fxmlLoader1.getController();
                up.setPublication(p);
                DevisModelContent.getChildren().clear();
                DevisModelContent.getChildren().add(DevisItem);
            } catch (IOException e) {
                e.printStackTrace();
            }





        });

    }

    @FXML
    void initialize() {
        assert pubItemContenu != null : "fx:id=\"pubItemContenu\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemDate != null : "fx:id=\"pubItemDate\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemDeleteBtn != null : "fx:id=\"pubItemDeleteBtn\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemDeleteBtnImg != null : "fx:id=\"pubItemDeleteBtnImg\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemImg != null : "fx:id=\"pubItemImg\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemProp != null : "fx:id=\"pubItemProp\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemShowBtn != null : "fx:id=\"pubItemShowBtn\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemShowBtnImg != null : "fx:id=\"pubItemShowBtnImg\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemTitre != null : "fx:id=\"pubItemTitre\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemUpdateBtn != null : "fx:id=\"pubItemUpdateBtn\" was not injected: check your FXML file 'PublicationItem.fxml'.";
        assert pubItemUpdateBtnImg != null : "fx:id=\"pubItemUpdateBtnImg\" was not injected: check your FXML file 'PublicationItem.fxml'.";

    }

}
