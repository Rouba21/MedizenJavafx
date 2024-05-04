module tn.esprit.projetpifinal {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;



    exports tn.esprit.projetpifinal;
    exports tn.esprit.projetpifinal.models;
    exports tn.esprit.projetpifinal.controllers;
    opens tn.esprit.projetpifinal.controllers to javafx.fxml;
    opens tn.esprit.projetpifinal.models;


}