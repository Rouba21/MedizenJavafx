package tn.esprit.controllers.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.controllers.ModifierEtablissement;
import tn.esprit.models.Departement;
import tn.esprit.services.departementservice;

import java.io.IOException;
import java.sql.SQLException;

public class CardControllerDept {

    @FXML
    private Label IDNom;

    @FXML
    private Label IDDescription;

    @FXML
    private Label IDChefDepartement;

    @FXML
    private Label IDServicesOfferts;

    @FXML
    private Label IDLocalisation; // Assuming there's a location for department

    private departementservice departementService = new departementservice();
    private Departement departement;

    private int id;

    public void setid(int id) {
        this.id = id;
    }

    @FXML
    void supprimerDepartement(ActionEvent event) throws SQLException {
        departementService.supprimer(id);

        Stage stage = (Stage) IDNom.getScene().getWindow();
        stage.close();
    }

    public void displayEtablissementDetails(Departement departement) {
        IDNom.setText("Nom: " + departement.getNom());
        IDDescription.setText("Description: " + departement.getDescription());
        IDChefDepartement.setText("Chef Département: " + departement.getChef_departement());
        IDServicesOfferts.setText("Services Offerts: " + departement.getService_offerts());
        IDLocalisation.setText("Localisation: " + departement.getLocalisation());
    }

    @FXML
    void modifierDepartement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/Etablissement/UpdateDepartement.fxml"));
            Parent root = loader.load();
            ModifierDepartement editDeptController = loader.getController();
            editDeptController.setData(departement);
            editDeptController.setid(id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Département");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue de modification : " + e.getMessage());
        }
    }

    public void setControllerDept(Departement departement) {
        this.departement = departement;
    }

    public void setControllerEtab(Departement departement) {
    this.departement = departement;}


}
