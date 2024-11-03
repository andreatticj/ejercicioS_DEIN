module eu.andreatt.ejercicios_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens eu.andreatt.ejercicios_dein to javafx.fxml;
    exports eu.andreatt.ejercicios_dein;
}