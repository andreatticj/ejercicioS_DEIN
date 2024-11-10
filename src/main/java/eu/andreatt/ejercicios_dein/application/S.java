package eu.andreatt.ejercicios_dein.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

/**
 * Clase principal de la aplicación, que extiende de {@link Application} y es responsable de inicializar la interfaz gráfica.
 * Esta clase carga el archivo FXML que define la interfaz de usuario, configura la ventana principal, y la muestra en pantalla.
 */
public class S extends Application {

    /**
     * Metodo que se ejecuta al iniciar la aplicación. Configura la ventana principal de la aplicación,
     * carga el archivo FXML correspondiente a la interfaz gráfica y establece los parámetros de la ventana.
     *
     * @param primaryStage El escenario principal de la aplicación, donde se muestra la ventana.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Carga el archivo FXML que contiene la interfaz gráfica.
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/eu/andreatt/ejercicios_dein/fxml/S.fxml"));

            // Crea una escena con el layout cargado y establece el tamaño de la ventana.
            Scene scene = new Scene(root, 1000, 820);

            // Establece el título de la ventana.
            primaryStage.setTitle("Veterinario");

            // Impide que la ventana cambie de tamaño.
            primaryStage.setResizable(false);

            // Asigna la escena al escenario principal.
            primaryStage.setScene(scene);

            // Carga el icono para la ventana utilizando una imagen desde los recursos del proyecto.
            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/ejercicios_dein/images/veterinaria.png"));
            primaryStage.getIcons().add(icon);

            // Muestra la ventana en pantalla.
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo principal de la aplicación que lanza la interfaz gráfica.
     * Este metodo invoca el lanzamiento de la aplicación {@link #start(Stage)}.
     *
     * @param args Argumentos de la línea de comandos, si los hubiera.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
