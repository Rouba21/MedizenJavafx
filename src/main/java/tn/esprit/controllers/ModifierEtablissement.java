package tn.esprit.controllers;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.models.Etablissement;
import tn.esprit.services.etablissementservice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.sql.SQLException;

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
            Notifications notification = Notifications.create()
                    .title("UPDATE Successfully ")
                    .text("Update Successfully Refrech")
                    .graphic(null) // You can set a graphic if needed
                    .hideAfter(Duration.seconds(5)) // Set how long the notification will be shown
                    .position(Pos.BOTTOM_RIGHT);
            notification.show();
            System.out.println(etablissement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}