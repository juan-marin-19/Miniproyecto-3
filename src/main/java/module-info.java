module com.example.miniproyecto_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyecto_3 to javafx.fxml;
    exports com.example.miniproyecto_3;
    exports com.example.miniproyecto_3.controler;
    opens com.example.miniproyecto_3.controler to javafx.fxml;
}