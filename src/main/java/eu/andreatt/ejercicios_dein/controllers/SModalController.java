package eu.andreatt.ejercicios_dein.controllers;

import eu.andreatt.ejercicios_dein.model.Veterinario;
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

/**
 * Controlador para la ventana modal que gestiona la información de un veterinario.
 */
public class SModalController {

	/** Botón para cancelar la acción. */
	@FXML
	private Button buttonCancelar;

	/** Botón para guardar la información del veterinario. */
	@FXML
	private Button buttonGuardar;

	/** Botón para seleccionar una imagen. */
	@FXML
	private Button buttonImage;

	/** Vista de la imagen seleccionada. */
	@FXML
	private ImageView imageView;

	/** Campo de texto para la edad del veterinario. */
	@FXML
	private TextField textFieldEdad;

	/** Campo de texto para la especie. */
	@FXML
	private TextField textFieldEspecie;

	/** Campo de texto para la fecha. */
	@FXML
	private TextField textFieldFecha;

	/** Campo de texto para el nombre. */
	@FXML
	private TextField textFieldNombre;

	/** Campo de texto para observaciones. */
	@FXML
	private TextField textFieldObservaciones;

	/** Campo de texto para el peso. */
	@FXML
	private TextField textFieldPeso;

	/** Campo de texto para la raza. */
	@FXML
	private TextField textFieldRaza;

	/** Campo de texto para el sexo. */
	@FXML
	private TextField textFieldSexo;

	/** Objeto Veterinario para almacenar los datos. */
	public Veterinario veterinario;

	/** Evento al pulsar el botón de cancelar. */
	@FXML
	void actionCancelar(ActionEvent event) {
		// Cerrar ventana modal
		Stage stage = (Stage) buttonCancelar.getScene().getWindow();
		stage.close();
	}

	/** Evento al pulsar el botón de guardar. */
	@FXML
	void actionGuardar(ActionEvent event) {
		// Validar datos introducidos
		String errores = validarDatos();

		// Mostrar alerta y añadir animal si es válida
		if (!errores.isEmpty()) {
			Alert alerta = generarVentana(AlertType.ERROR, errores, "ERROR");
			alerta.show();
		} else {
			// Obtener la imagen como byte[]
			byte[] imagen = obtenerImagenComoByteArray();

			// Crear un nuevo objeto Veterinario con todos los datos
			veterinario = new Veterinario(
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
			System.out.println(veterinario);

			// Cerrar ventana
			Stage stage = (Stage) buttonCancelar.getScene().getWindow();
			stage.close();
		}
	}

	/** Evento al pulsar el botón para seleccionar una imagen. */
	@FXML
	void actionSeleccionarImagen(ActionEvent event) {
		// Crear un FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.gif"));

		// Mostrar el diálogo para seleccionar el archivo
		File archivo = fileChooser.showOpenDialog(buttonImage.getScene().getWindow());
		if (archivo != null) {
			// Cargar la imagen seleccionada en el ImageView
			try {
				BufferedImage bufferedImage = ImageIO.read(archivo);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				imageView.setImage(image);
			} catch (IOException e) {
				e.printStackTrace();
				Alert alerta = generarVentana(AlertType.ERROR, "Error al cargar la imagen: " + e.getMessage(), "ERROR");
				alerta.show();
			}
		}
	}

	/** Método para obtener la imagen como byte[]. */
	private byte[] obtenerImagenComoByteArray() {
		if (imageView.getImage() != null) {
			// Obtener la imagen de ImageView
			Image image = imageView.getImage();

			// Convertir Image a BufferedImage
			BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			try {
				// Escribir BufferedImage en el ByteArrayOutputStream
				ImageIO.write(bufferedImage, "png", baos);
				return baos.toByteArray(); // Retornar la imagen como byte[]
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null; // Retornar null si no hay imagen
	}

	/** Validar los datos introducidos en los campos de texto. */
	private String validarDatos() {
		StringBuilder errores = new StringBuilder();

		// Validar nombre
		if (textFieldNombre.getText().trim().isEmpty()) {
			errores.append("Se debe escribir el nombre\n");
		}

		// Validar especie
		if (textFieldEspecie.getText().trim().isEmpty()) {
			errores.append("Se debe escribir la especie\n");
		}

		// Validar raza
		if (textFieldRaza.getText().trim().isEmpty()) {
			errores.append("Se debe escribir la raza\n");
		}

		// Validar sexo
		if (textFieldSexo.getText().trim().isEmpty()) {
			errores.append("Se debe escribir el sexo\n");
		}

		// Validar fecha
		if (textFieldFecha.getText().trim().isEmpty()) {
			errores.append("Se debe escribir una fecha\n");
		}

		// Validar edad
		if (textFieldEdad.getText().trim().isEmpty()) {
			errores.append("Se debe escribir una edad\n");
		} else {
			try {
				Integer.parseInt(textFieldEdad.getText());
			} catch (NumberFormatException e) {
				errores.append("La edad debe ser un número\n");
			}
		}

		// Validar peso
		if (textFieldPeso.getText().trim().isEmpty()) {
			errores.append("Se debe escribir el peso\n");
		} else {
			try {
				Float.parseFloat(textFieldPeso.getText());
			} catch (NumberFormatException e) {
				errores.append("El peso debe ser un número\n");
			}
		}

		return errores.toString();
	}

	/** Generar una ventana de alerta. */
	private Alert generarVentana(AlertType tipoDeAlerta, String mensaje, String title) {
		Alert alerta = new Alert(tipoDeAlerta);
		alerta.setContentText(mensaje);
		alerta.setHeaderText(null);
		alerta.setTitle(title);
		return alerta;
	}

	/** Getters y setters para los campos de texto. */
	public TextField getTextFieldEdad() {
		return textFieldEdad;
	}

	public void setTextFieldEdad(String textFieldEdad) {
		this.textFieldEdad.setText(textFieldEdad);
	}

	public TextField getTextFieldEspecie() {
		return textFieldEspecie;
	}

	public void setTextFieldEspecie(String textFieldEspecie) {
		this.textFieldEspecie.setText(textFieldEspecie);
	}

	public TextField getTextFieldFecha() {
		return textFieldFecha;
	}

	public void setTextFieldFecha(String textFieldFecha) {
		this.textFieldFecha.setText(textFieldFecha);
	}

	public TextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public void setTextFieldNombre(String textFieldNombre) {
		this.textFieldNombre.setText(textFieldNombre);
	}

	public TextField getTextFieldObservaciones() {
		return textFieldObservaciones;
	}

	public void setTextFieldObservaciones(String textFieldObservaciones) {
		this.textFieldObservaciones.setText(textFieldObservaciones);
	}

	public TextField getTextFieldPeso() {
		return textFieldPeso;
	}

	public void setTextFieldPeso(String textFieldPeso) {
		this.textFieldPeso.setText(textFieldPeso);
	}

	public TextField getTextFieldRaza() {
		return textFieldRaza;
	}

	public void setTextFieldRaza(String textFieldRaza) {
		this.textFieldRaza.setText(textFieldRaza);
	}

	public TextField getTextFieldSexo() {
		return textFieldSexo;
	}

	public void setTextFieldSexo(String textFieldSexo) {
		this.textFieldSexo.setText(textFieldSexo);
	}
}
