package tn.esprit.controllers.FrontController;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import tn.esprit.models.Departement;
import tn.esprit.services.departementservice;

import java.net.URL;
import java.util.ResourceBundle;

public class FrontDepartmentRow implements Initializable {


    @FXML
    private TextField Description;

    @FXML
    private TextField Localisation;

    @FXML
    private TextField chefdepartment;

    @FXML
    private ImageView img_user;

    @FXML
    private TextField nom;

    @FXML
    private TextField serv_offert;


    private StackPane area;

    departementservice cs = new departementservice();

    int id ;
    public void setId(int id) {
        this.id=id;

    }


    private FrontDepartment frontController; // Reference to the task_front controller
    public void setFrontController(FrontDepartment frontController) {
        this.frontController = frontController;
    }

    public void setArea(StackPane area) {
        this.area = area;
    }
    public void setData(Departement departement){

        // Image imageprofile = new Image(String.valueOf(getClass().getResource("image/admin-22.jpg")));
        //  img_user.setImage(imageprofile);
        nom.setText(departement.getNom());
        Description.setText(departement.getDescription());
        Localisation.setText(departement.getLocalisation());
        chefdepartment.setText(departement.getChef_departement());
        serv_offert.setText(departement.getService_offerts());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}