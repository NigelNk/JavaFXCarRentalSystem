module com.example.carrentalsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires javafx.base;



    opens com.example.carrentalsystem to javafx.fxml;
    exports com.example.carrentalsystem;
    exports com.example.carrentalsystem.backend;
    opens com.example.carrentalsystem.backend;
}