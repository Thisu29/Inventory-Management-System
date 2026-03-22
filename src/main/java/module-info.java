module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.Controllers to javafx.fxml;
    exports com.example.Controllers;
    exports Models;
    opens Models to javafx.fxml;
    exports Managers;
    opens Managers to javafx.fxml;
}