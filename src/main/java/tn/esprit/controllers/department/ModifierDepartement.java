package tn.esprit.controllers.department;

import tn.esprit.models.Departement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.services.departementservice;

import java.sql.SQLException;

public class ModifierDepartement {
    @FXML
    private TextField IDNom;

    @FXML
    private TextField IDDescription;

    @FXML
    private TextField IDChefDepartement;

    @FXML
    private TextField IDServicesOfferts;

    @FXML
    private TextField IDLocalisation;

    private departementservice departementService = new departementservice();

    private Departement departement;
    int idd;

    public void setid(int id) {
        this.idd = id;
    }

    public void setData(Departement departement) {
        this.departement = departement;
        IDNom.setText(departement.getNom());
        IDDescription.setText(departement.getDescription());
        IDChefDepartement.setText(departement.getChef_departement());
        IDServicesOfferts.setText(departement.getService_offerts());
        IDLocalisation.setText(departement.getLocalisation());
    }

    @FXML
    void ModifierBtn(ActionEvent event) {
        try {
            int id = idd;
            Departement departement = new Departement(IDNom.getText(),
                    IDDescription.getText(),
                    IDChefDepartement.getText(),
                    IDServicesOfferts.getText(),
                    IDLocalisation.getText());

            departement.setId(id);
            departementService.modifier(departement);
            System.out.println(departement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
