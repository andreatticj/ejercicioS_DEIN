package eu.andreatt.ejercicios_dein.controllers;

import eu.andreatt.ejercicios_dein.model.Animal;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;

/**
 * Controlador para la ventana modal que gestiona la información de un veterinario.
 * Permite ingresar y modificar los datos de un animal, incluyendo la carga de una imagen.
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
	private DatePicker fecha;

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

	/** Objeto Animal que contiene los datos que se gestionan en la ventana. */
	private Animal animal;

	/** Imagen del animal en formato Blob. */
	private Blob imagen;

	/**
	 * Acción que se ejecuta cuando el usuario cancela la operación. Cierra la ventana modal sin realizar cambios.
	 *
	 * @param event El evento de acción generado por el usuario.
	 */
	@FXML
	void actionCancelar(ActionEvent event) {
		Stage stage = (Stage) buttonCancelar.getScene().getWindow();
		stage.close();
	}

	/**
	 * Acción que se ejecuta cuando el usuario guarda los datos ingresados. Valida los datos y, si son correctos,
	 * crea un nuevo objeto {@link Animal} con los valores introducidos.
	 *
	 * @param event El evento de acción generado por el usuario.
	 */
	@FXML
	void actionGuardar(ActionEvent event) {
		String errores = validarDatos();

		// Si hay errores en los datos, muestra una alerta con los mensajes de error.
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
					fecha.getValue(),
					imagen
			);
			System.out.println(animal);
			Stage stage = (Stage) buttonCancelar.getScene().getWindow();
			stage.close();
		}
	}

	/**
	 * Acción que se ejecuta cuando el usuario selecciona una imagen para el animal. Permite cargar una imagen desde
	 * el sistema de archivos y mostrarla en el {@link ImageView}.
	 *
	 * @param event El evento de acción generado por el usuario.
	 */
	@FXML
	void actionSeleccionarImagen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.gif"));

		// Muestra un cuadro de diálogo para seleccionar un archivo de imagen.
		File archivo = fileChooser.showOpenDialog(buttonImage.getScene().getWindow());
		if (archivo != null) {
			try {
				// Convierte la imagen seleccionada en un formato adecuado para el ImageView.
				BufferedImage bufferedImage = ImageIO.read(archivo);
				Image imagenFX = SwingFXUtils.toFXImage(bufferedImage, null);
				imageView.setImage(imagenFX);
			} catch (IOException e) {
				// Muestra una alerta en caso de error al cargar la imagen.
				generarVentana(AlertType.ERROR, "Error al cargar la imagen: " + e.getMessage(), "ERROR").show();
			}
		}
	}

	/**
	 * Valida los datos ingresados en los campos de texto. Verifica que los campos obligatorios no estén vacíos y que
	 * los valores numéricos (edad y peso) sean correctos.
	 *
	 * @return Una cadena de texto que contiene los errores encontrados, o una cadena vacía si no hay errores.
	 */
	private String validarDatos() {
		StringBuilder errores = new StringBuilder();

		// Valida que los campos no estén vacíos y que los valores numéricos sean correctos.
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

	/**
	 * Genera una ventana de alerta con el tipo de alerta, el mensaje y el título especificados.
	 *
	 * @param tipo El tipo de alerta que se mostrará.
	 * @param mensaje El mensaje que se mostrará en la alerta.
	 * @param titulo El título de la alerta.
	 * @return La ventana de alerta configurada.
	 */
	private Alert generarVentana(AlertType tipo, String mensaje, String titulo) {
		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		return alerta;
	}

	/**
	 * Carga los datos de un animal existente en los campos de la ventana modal.
	 *
	 * @param animal El objeto {@link Animal} con los datos que se cargarán en los campos.
	 */
	public void cargarDatosAnimal(Animal animal) {
		// Carga los datos del animal en los TextFields.
		setTextFieldNombre(animal.getNombre());
		setTextFieldEspecie(animal.getEspecie());
		setTextFieldRaza(animal.getRaza());
		setTextFieldSexo(animal.getSexo());
		setTextFieldEdad(String.valueOf(animal.getEdad()));
		setTextFieldPeso(String.valueOf(animal.getPeso()));
		setTextFieldObservaciones(animal.getObservaciones());
		setFecha(animal.getFecha());

		// Carga la imagen del animal si está disponible.
		if (animal.getImagen() != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(animal.getImagen().getBinaryStream());
				Image imagenFX = SwingFXUtils.toFXImage(bufferedImage, null);
				imageView.setImage(imagenFX);
			} catch (IOException | SQLException e) {
				// Muestra una alerta en caso de error al cargar la imagen.
				generarVentana(AlertType.ERROR, "Error al cargar la imagen: " + e.getMessage(), "ERROR").show();
			}
		}
	}

	/**
	 * Obtiene el objeto {@link Animal} asociado a la ventana modal.
	 *
	 * @return El objeto {@link Animal} con los datos ingresados en la ventana.
	 */
	public Animal getAnimal() {
		return animal;
	}

	/**
	 * Establece el objeto {@link Animal} asociado a la ventana modal.
	 *
	 * @param animal El objeto {@link Animal} que se asociará a la ventana.
	 */
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	// Métodos para establecer los valores de los campos de texto.

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

	public void setFecha(LocalDate fecha) {
		this.fecha.setValue(fecha);
	}

	public Blob getImagen() {
		return imagen;
	}

	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}
}
