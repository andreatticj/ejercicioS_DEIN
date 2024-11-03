package eu.andreatt.ejercicios_dein.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

public class S extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {			
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/S.fxml"));
            Scene scene = new Scene(root,1000,820);
            primaryStage.setTitle("Veterinario");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            
			Image icon = new Image(getClass().getResourceAsStream("/images/veterinaria.png"));
			primaryStage.getIcons().add(icon);			
			
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}