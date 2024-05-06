module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;
    requires java.mail;
    requires java.desktop;

    exports Medizen.Controllers to javafx.fxml;
    exports com.example.test to javafx.graphics;
    opens Medizen.Controllers to javafx.fxml; // If your controllers use reflection, such as FXML loading.
}
