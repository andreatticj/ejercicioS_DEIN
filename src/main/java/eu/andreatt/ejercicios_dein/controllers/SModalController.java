package eu.andreatt.ejercicios_dein.controllers;

import eu.andreatt.ejercicios_dein.model.Animal;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Controlador para la ventana modal que gestiona la información de un veterinario.
 */
public class SModalController {

	@FXML
	private Button buttonCancelar;

	@FXML
	private Button buttonGuardar;

	@FXML
	private Button buttonImage;

	@FXML
	private ImageView imageView;

	@FXML
	private TextField textFieldEdad;

	@FXML
	private TextField textFieldEspecie;

	@FXML
	private TextField textFieldFecha;

	@FXML
	private TextField textFieldNombre;

	@FXML
	private TextField textFieldObservaciones;

	@FXML
	private TextField textFieldPeso;

	@FXML
	private TextField textFieldRaza;

	@FXML
	private TextField textFieldSexo;

	private Animal animal;
	private Blob imagen; // Imagen del animal en formato Blob

	@FXML
	void actionCancelar(ActionEvent event) {
		Stage stage = (Stage) buttonCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	void actionGuardar(ActionEvent event) {
		String errores = validarDatos();

		if (!errores.isEmpty()) {
			Alert alerta = generarVentana(AlertType.ERROR, errores, "ERROR");
			alerta.show();
		} else {
			animal = new Animal(
					textFieldNombre.getText(),
					textFieldEspecie.getText(),
					textFieldRaza.getText(),
					textFieldSexo.getText(),
					Integer.parseInt(textFieldEdad.getText()),
					Float.parseFloat(textFieldPeso.getText()),
					textFieldObservaciones.getText(),
					textFieldFecha.getText(),
					imagen
			);
			System.out.println(animal);
			Stage stage = (Stage) buttonCancelar.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	void actionSeleccionarImagen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.gif"));

		File archivo = fileChooser.showOpenDialog(buttonImage.getScene().getWindow());
		if (archivo != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(archivo);
				Image imagenFX = SwingFXUtils.toFXImage(bufferedImage, null);
				imageView.setImage(imagenFX);
			} catch (IOException e) {
				generarVentana(AlertType.ERROR, "Error al cargar la imagen: " + e.getMessage(), "ERROR").show();
			}
		}
	}

	private String validarDatos() {
		StringBuilder errores = new StringBuilder();

		if (textFieldNombre.getText().isEmpty()) {
			errores.append("* El nombre no puede estar vacío.\n");
		}
		if (textFieldEspecie.getText().isEmpty()) {
			errores.append("* La especie no puede estar vacía.\n");
		}
		if (textFieldRaza.getText().isEmpty()) {
			errores.append("* La raza no puede estar vacía.\n");
		}
		if (textFieldSexo.getText().isEmpty()) {
			errores.append("* El sexo no puede estar vacío.\n");
		}
		try {
			int edad = Integer.parseInt(textFieldEdad.getText());
			if (edad <= 0) {
				errores.append("* La edad debe ser un número positivo.\n");
			}
		} catch (NumberFormatException e) {
			errores.append("* La edad debe ser un número.\n");
		}
		try {
			float peso = Float.parseFloat(textFieldPeso.getText());
			if (peso <= 0) {
				errores.append("* El peso debe ser un número positivo.\n");
			}
		} catch (NumberFormatException e) {
			errores.append("* El peso debe ser un número.\n");
		}

		return errores.toString();
	}

	private Alert generarVentana(AlertType tipo, String mensaje, String titulo) {
		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		return alerta;
	}

	public void cargarDatosAnimal(Animal animal) {
		// Cargar los datos del animal en los TextFields
		setTextFieldNombre(animal.getNombre());
		setTextFieldEspecie(animal.getEspecie());
		setTextFieldRaza(animal.getRaza());
		setTextFieldSexo(animal.getSexo());
		setTextFieldEdad(String.valueOf(animal.getEdad()));
		setTextFieldPeso(String.valueOf(animal.getPeso()));
		setTextFieldObservaciones(animal.getObservaciones());
		setTextFieldFecha(animal.getFecha());

		// Cargar la imagen
		if (animal.getImagen() != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(animal.getImagen().getBinaryStream());
				Image imagenFX = SwingFXUtils.toFXImage(bufferedImage, null);
				imageView.setImage(imagenFX);
			} catch (IOException | SQLException e) {
				generarVentana(AlertType.ERROR, "Error al cargar la imagen: " + e.getMessage(), "ERROR").show();
			}
		}
	}


	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public void setTextFieldNombre(String nombre) {
		this.textFieldNombre.setText(nombre);
	}

	public void setTextFieldEspecie(String especie) {
		this.textFieldEspecie.setText(especie);
	}

	public void setTextFieldRaza(String raza) {
		this.textFieldRaza.setText(raza);
	}

	public void setTextFieldSexo(String sexo) {
		this.textFieldSexo.setText(sexo);
	}

	public void setTextFieldEdad(String edad) {
		this.textFieldEdad.setText(edad);
	}

	public void setTextFieldPeso(String peso) {
		this.textFieldPeso.setText(peso);
	}

	public void setTextFieldObservaciones(String observaciones) {
		this.textFieldObservaciones.setText(observaciones);
	}

	public void setTextFieldFecha(String fecha) {
		this.textFieldFecha.setText(fecha);
	}

	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}
}
