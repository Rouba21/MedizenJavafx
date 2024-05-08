module tn.esprit {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires jdk.jsobject;
    requires org.controlsfx.controls;


    exports tn.esprit.models;
    exports tn.esprit.controllers;
    exports tn.esprit.controllers.department;
    exports tn.esprit.controllers.FrontController;
    opens tn.esprit.controllers to javafx.fxml;
    opens tn.esprit.controllers.department to javafx.fxml;
    opens tn.esprit.controllers.FrontController to javafx.fxml;
    opens tn.esprit.test to javafx.fxml;
    opens tn.esprit.models;
    exports tn.esprit.test;


}