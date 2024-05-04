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


    @FXML
    void ModifierBtn(ActionEvent event) {
        try {
            int id = Integer.parseInt(IDname.getText());
            BigDecimal longitude = new BigDecimal(IDLongitude.getText());  // Assuming IDLongitude is a TextField for longitude
            BigDecimal latitude = new BigDecimal(IDLatitude.getText());    // Assuming IDLatitude is a TextField for latitude
            Etablissement etablissement = new Etablissement(id,
                    IDLocalisation.getText(),
                    IDtype.getText(),
                    IDname.getText(),
                    IDdescrp.getText(),
                    longitude,
                    latitude);  // Assuming IDdescrp is a TextField for description
            sp.modifier(etablissement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void setData(Etablissement etablissement){
        this.etablissement= etablissement;
        IDname.setText(String.valueOf(etablissement.getName()));
        IDtype.setText(etablissement.getTypee());
        IDdescrp.setText(etablissement.getDescription());
        IDname.setText(etablissement.getName());
        IDLatitude.setText(String.valueOf(etablissement.getLatitude()));
        IDLongitude.setText(String.valueOf(etablissement.getLongitude()));
    }
}