package tn.esprit.projetpifinal.controllers;

import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.projetpifinal.models.Etablissement;
import tn.esprit.projetpifinal.service.etablissementservice;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ModifierEtablissement {
    @FXML
    private TextField IDLatitude;

    @FXML
    private TextField IDLocalisation;

    @FXML
    private TextField IDLongitude;

    @FXML
    private TextField IDdescrp;

    @FXML
    private TextField IDname;

    @FXML
    private TextField IDtype;
    etablissementservice sp = new etablissementservice();

    private Etablissement etablissement;
    int idd ;
    public void setid(int id) {
        this.idd = id ;
    }

    public void setData(Etablissement etablissement){
        this.etablissement= etablissement;
        IDname.setText(etablissement.getName());
        IDLocalisation.setText(etablissement.getLocation());
        IDtype.setText(etablissement.getTypee())   ;
        IDdescrp.setText(etablissement.getDescription());
        IDLatitude.setText(String.valueOf(etablissement.getLatitude()));
        IDLongitude.setText(String.valueOf(etablissement.getLongitude()));
    }
    @FXML
    void ModifierBtn(ActionEvent event) {
        try {

            int id = idd;
            BigDecimal longitude = new BigDecimal(IDLongitude.getText());  // Assuming IDLongitude is a TextField for longitude
            BigDecimal latitude = new BigDecimal(IDLatitude.getText());    // Assuming IDLatitude is a TextField for latitude
            Etablissement etablissement = new  Etablissement(id,IDtype.getText(),
                    IDdescrp.getText(),
                    IDLocalisation.getText(),
                    IDname.getText(),
                    latitude,
                    longitude
                    );  // Assuming IDdescrp is a TextField for description
            sp.modifier(etablissement);
            System.out.println(etablissement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}