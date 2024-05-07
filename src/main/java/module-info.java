module tn.esprit.projetpifinal {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires jdk.jsobject;


    exports tn.esprit.projetpifinal;
    exports tn.esprit.projetpifinal.models;
    exports tn.esprit.projetpifinal.controllers;
    exports tn.esprit.projetpifinal.controllers.FrontController;
    opens tn.esprit.projetpifinal.controllers to javafx.fxml;
    opens tn.esprit.projetpifinal.controllers.FrontController to javafx.fxml;
    opens tn.esprit.projetpifinal.models;


}