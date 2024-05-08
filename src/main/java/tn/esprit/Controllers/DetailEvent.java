package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Event;

public class DetailEvent {
    @FXML
    private AnchorPane logout;

    @FXML
    private Button logoutt;

    @FXML
    private Label welcomeid;

    @FXML
    void etablissement_btn(ActionEvent event) {

    }

    @FXML
    void event_btn(ActionEvent event) {

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
    private Label dated;

    @FXML
    private Label datefin;

    @FXML
    private Label desc;

    @FXML
    private ImageView image_detail;

    @FXML
    private Label lieu;

    @FXML
    private Label titred;


    public void initData(Event event) {
        titred.setText(event.getTitre());
        desc.setText(event.getDescription());
        dated.setText("Date début : " + event.getFormattedDateDebut());
        datefin.setText("Date fin : " + event.getFormattedDateFin());
        lieu.setText("Lieu : " + event.getLieu());
        desc.setText("Description : " + event.getDescription());


        String imageUrl = event.getImageURL();


        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {

                Image image = new Image(imageUrl);


                image_detail.setImage(image);


                image_detail.setFitWidth(150); // Largeur souhaitée de l'image
                image_detail.setFitHeight(150); // Hauteur souhaitée de l'image
                image_detail.setPreserveRatio(true); // Préserver le rapport largeur/hauteur de l'image
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());

            }
        } else {

            image_detail.setImage(null);
        }
    }
}
