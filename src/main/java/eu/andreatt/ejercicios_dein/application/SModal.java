package eu.andreatt.ejercicios_dein.application;

import eu.andreatt.ejercicios_dein.controllers.SModalController;
import eu.andreatt.ejercicios_dein.model.Animal;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;

/**
 * Clase que representa una ventana modal para la creación o modificación de un animal en la base de datos.
 * La ventana se muestra como un cuadro de diálogo modal, bloqueando la interacción con otras ventanas de la aplicación
 * hasta que se cierre.
 */
public class SModal extends Stage {

    /** Controlador asociado a la ventana modal. */
    private SModalController controller;

    /**
     * Constructor para la creación de una nueva instancia de la clase {@link Animal}.
     * Inicializa la interfaz gráfica cargando el archivo FXML correspondiente y configurando los parámetros de la ventana.
     * La ventana se muestra como un cuadro de diálogo modal.
     */
    public SModal() {
        try {
            // Carga el archivo FXML que contiene la interfaz gráfica de la ventana modal.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/ejercicios_dein/fxml/S_modal.fxml"));

            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            controller = loader.getController();

            // Configura la escena y la ventana modal.
            Scene scene = new Scene(root);
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Nuevo Animal");
            setResizable(false);

            setScene(scene);

            // Establece el icono de la ventana.
            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/ejercicios_dein/images/veterinaria.png"));
            getIcons().add(icon);

            // Muestra la ventana modal y espera a que se cierre.
            showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor para la modificación de un {@link Animal} existente.
     * Carga los datos del animal en los campos correspondientes de la interfaz y configura la ventana modal.
     * La ventana se muestra como un cuadro de diálogo modal.
     *
     * @param a El animal que se desea modificar.
     */
    public SModal(Animal a) {
        try {
            // Carga el archivo FXML de la interfaz de la ventana modal.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/ejercicios_dein/fxml/S_modal.fxml"));

            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            controller = loader.getController();

            // Rellena los campos de la interfaz con los datos del animal a modificar.
            controller.setTextFieldNombre(a.getNombre());
            controller.setTextFieldEspecie(a.getEspecie());
            controller.setTextFieldRaza(a.getRaza());
            controller.setTextFieldSexo(a.getSexo());
            controller.setTextFieldEdad(a.getEdad()+"");
            controller.setTextFieldPeso(a.getPeso()+"");
            controller.setTextFieldObservaciones(a.getObservaciones());
            controller.setFecha(a.getFecha());

            // Cargar la imagen si existe
            if (a.getImagen() != null) {
                try {
                    controller.getImageView().setImage(new Image(a.getImagen().getBinaryStream()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Configura la escena y la ventana modal.
            Scene scene = new Scene(root);
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Modificar Animal");
            setResizable(false);

            setScene(scene);

            // Establece el icono de la ventana.
            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/ejercicios_dein/images/veterinaria.png"));
            getIcons().add(icon);

            // Muestra la ventana modal y espera a que se cierre.
            showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el controlador asociado a la ventana modal.
     *
     * @return El controlador de la ventana modal {@link SModalController}.
     */
    public SModalController getController() {
        return controller;
    }
}