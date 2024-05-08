package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Sponseur;

public class DetailSponseurController {

    @FXML
    private Label emaild;

    @FXML
    private ImageView imade_detail;

    @FXML
    private AnchorPane logout;

    @FXML
    private Button logoutt;

    @FXML
    private Label nomd;

    @FXML
    private Label numerod;

    @FXML
    private Label packd;

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
    private Image image;

    public void initData(Sponseur sponseur) {
        if (sponseur != null) {
            // Afficher les données du sponsor dans les éléments correspondants de l'interface
            nomd.setText("Nom : " + sponseur.getNom());
            emaild.setText("Email : " + sponseur.getEmail());
            numerod.setText("Numero : " + sponseur.getNumero());
            packd.setText("Pack : " + sponseur.getPack());


            // Charger et afficher l'image du logo du sponsor s'il existe
            String logoPath = sponseur.getLogo();
            if (logoPath != null && !logoPath.isEmpty()) {
                Image image = new Image("file:" + logoPath);
                imade_detail.setImage(image);
            } else {
                // Effacer l'image si aucune image n'est disponible
                imade_detail.setImage(null);
            }
        }
    }

    @FXML
    private Label descd;
}
