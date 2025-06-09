module com.example.miniproyecto_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.example.miniproyecto_3.model;

    opens com.example.miniproyecto_3 to javafx.fxml;
    exports com.example.miniproyecto_3;
    exports com.example.miniproyecto_3.controller;
    opens com.example.miniproyecto_3.controller to javafx.fxml;
}