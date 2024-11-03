package eu.andreatt.ejercicios_dein.application;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.io.IOException;

import controllers.SModalController;
import javafx.fxml.FXMLLoader;

public class SModal extends Stage {
	
	private SModalController controller;
	
    public SModal() {
        try {			           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/S_modal.fxml"));
            
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            controller = loader.getController();
                        
            Scene scene = new Scene(root);
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Nuevo Animal");
            setResizable(false);

            setScene(scene);
            
			Image icon = new Image(getClass().getResourceAsStream("/images/veterinaria.png"));
			getIcons().add(icon);			
			
			showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public SModal(String nombre, String especie, String raza, String sexo, int edad, float peso, String observaciones, String fecha) {
        try {			           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/S_modal.fxml"));
            
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            controller = loader.getController();
            controller.setTextFieldNombre(nombre);
            controller.setTextFieldEspecie(especie);
            controller.setTextFieldRaza(raza);
            controller.setTextFieldSexo(sexo);
            controller.setTextFieldEdad(edad+"");
            controller.setTextFieldPeso(peso+"");
            controller.setTextFieldObservaciones(observaciones);
            controller.setTextFieldFecha(fecha);        
                        
            Scene scene = new Scene(root);
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Modificar Animal");
            setResizable(false);

            setScene(scene);
            
			Image icon = new Image(getClass().getResourceAsStream("/images/veterinaria.png"));
			getIcons().add(icon);			
			
			showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public SModalController getController() {
        return controller;
    }
}