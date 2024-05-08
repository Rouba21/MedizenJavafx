package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import tn.esprit.models.Commentaire;
import tn.esprit.services.CommentaireServiceMontaha;
import java.util.Optional;

public class AffichComment {

    @FXML
    private Label username;

    @FXML
    private Pane lcomment2;

    @FXML
    private Label lcomment;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    void onDeleteClicked(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce commentaire ?");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                CommentaireServiceMontaha commentaireService = new CommentaireServiceMontaha();
                commentaireService.delete(id);
                // Rechargez la vue pour refléter les changements
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void setData(Commentaire commentaire) {
        lcomment.setText(commentaire.getContenu() + "\n" + commentaire.getDatedecreation());
        // Set other attributes of Commentaire as needed
    }
}
