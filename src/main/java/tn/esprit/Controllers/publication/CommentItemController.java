package tn.esprit.Controllers.publication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Publication;
import tn.esprit.services.CommentaireService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CommentItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text commentDate;

    @FXML
    private Text commentTime;

    @FXML
    private ImageView commenterImg;

    @FXML
    private ImageView deleteBtn;

    @FXML
    private Text pubComment;

    @FXML
    private Text pubCommenter;

    public void setData(Commentaire c){
        pubComment.setText(c.getContenu());
        commentDate.setText(String.valueOf(c.getDatedecreation()));
        deleteBtn.setOnMouseClicked(event -> {
            CommentaireService commentaireService = new CommentaireService();
            try {
                commentaireService.delete(c.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichePub.fxml"));
            try {
                Parent root = loader.load();
                VBox contentArea = (VBox) ((Node) event.getSource()).getScene().lookup("#Area");



                Publication publl = PublicationItemController.getPublicationToGetLikes();


                CommentaireService commentaireService1=new CommentaireService();

                List<Commentaire> commentaires=commentaireService1.getCommentsForPub(publl.getId());
                contentArea.getChildren().clear();
                for (int i = 0; i < commentaires.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/publicationInterfaces/CommentItem.fxml"));
                    HBox pubItem = fxmlLoader.load();
                    CommentItemController userItemController = fxmlLoader.getController();
                    userItemController.setData(commentaires.get(i));
                    contentArea.getChildren().add(pubItem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });

    }

    @FXML
    void initialize() {
        assert commentDate != null : "fx:id=\"commentDate\" was not injected: check your FXML file 'CommentItem.fxml'.";
        assert commentTime != null : "fx:id=\"commentTime\" was not injected: check your FXML file 'CommentItem.fxml'.";
        assert commenterImg != null : "fx:id=\"commenterImg\" was not injected: check your FXML file 'CommentItem.fxml'.";
        assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'CommentItem.fxml'.";
        assert pubComment != null : "fx:id=\"pubComment\" was not injected: check your FXML file 'CommentItem.fxml'.";
        assert pubCommenter != null : "fx:id=\"pubCommenter\" was not injected: check your FXML file 'CommentItem.fxml'.";

    }

}
