package tn.esprit.controllers.FrontController;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Departement;
import tn.esprit.services.departementservice;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FrontDepartment implements Initializable {

    @FXML
    private VBox Candidature_Ligne_Info;
    @FXML
    public StackPane contentArea;
    int idetab;
    public void setId_dep(int id) throws IOException {
        this.idetab = id ;
        Refrech_page();
    }
    departementservice cs = new departementservice();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Refrech_page();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Departement> condidature = null;
        try {
            condidature = cs.afficherById(idetab);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i< condidature.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/tn/esprit/Etablissement/ObjetDepartment.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                FrontDepartmentRow obj = fxmlLoader.getController();

                obj.setArea(contentArea);
                obj.setData(condidature.get(i));
                obj.setId(condidature.get(i).getId());

                Candidature_Ligne_Info.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }



    public void Refrech_page() throws IOException {
        List<Departement> condidature = null;
        try {
            condidature = cs.afficherById(idetab);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i< condidature.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/tn/esprit/Etablissement/ObjetDepartment.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                FrontDepartmentRow obj = fxmlLoader.getController();

                obj.setArea(contentArea);
                obj.setData(condidature.get(i));
                obj.setId(condidature.get(i).getId());

                Candidature_Ligne_Info.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public void refreche() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/tn/esprit/Etablissement/ObjetDepartment.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }


}