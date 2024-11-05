module eu.andreatt.ejercicios_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.swing;

    opens eu.andreatt.ejercicios_dein.controllers to javafx.fxml;
    opens eu.andreatt.ejercicios_dein.application to javafx.graphics;
    opens eu.andreatt.ejercicios_dein.model to javafx.base;

    exports eu.andreatt.ejercicios_dein.application;
}