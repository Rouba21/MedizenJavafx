package tn.esprit.Controllers.publication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.Controllers.CardTopic;
import tn.esprit.models.Publication;
import tn.esprit.models.Topic;
import tn.esprit.services.PublicationServiceMontaha;
import tn.esprit.services.TopicServiceMontaha;

public class PublicationsListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text WindowTitle;

    @FXML
    private Button ajout_pubBtn;

    @FXML
    private VBox pubListContainer;

    @FXML
    private ComboBox<?> roleInput;

    @FXML
    private HBox updateUserModel;

    @FXML
    private VBox updateUserModelContent;

    @FXML
    private Text userListTitle;

    @FXML
    private Pane userPane;

    @FXML
    private HBox userTableHead;

    @FXML
    void ajouterUser(MouseEvent event) {
        updateUserModel.setVisible(true);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/AjouterPub.fxml"));
            VBox x =null;
            x=fxmlLoader.load();
            updateUserModelContent.getChildren().clear();
            updateUserModelContent.getChildren().add(x);
        }catch (IOException e){
            throw new RuntimeException();
        }

    }

    @FXML
    void close_updateUserModel(MouseEvent event) {
        updateUserModel.setVisible(false);

    }

    @FXML
    void roleChange(ActionEvent event) {

    }

    @FXML
    void initialize() {
        try {
            Topic topic=CardTopic.getTopicToGetPublication();
            TopicServiceMontaha topicService=new TopicServiceMontaha();
            PublicationServiceMontaha publicationService=new PublicationServiceMontaha();
          //  = topicService.getPublicationsForTopic(topic.getId());
            List<Publication> pubsList=publicationService.getAll();
            for (int i = 0; i < pubsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/publicationInterfaces/PublicationItem.fxml"));
                HBox pubItem = fxmlLoader.load();
                PublicationItemController userItemController = fxmlLoader.getController();
                userItemController.setData(pubsList.get(i));
                pubListContainer.getChildren().add(pubItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
