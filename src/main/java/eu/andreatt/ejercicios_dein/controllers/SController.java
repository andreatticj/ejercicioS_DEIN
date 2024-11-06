package eu.andreatt.ejercicios_dein.controllers;

import eu.andreatt.ejercicios_dein.application.SModal;
import eu.andreatt.ejercicios_dein.dao.VeterinarioDao;
import eu.andreatt.ejercicios_dein.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador para la gestión de veterinarios en la aplicación.
 * Proporciona funcionalidades para agregar, eliminar y modificar registros
 * de veterinarios en una tabla de la interfaz gráfica.
 */
public class SController {

	@FXML
	private TableColumn<Animal, Integer> tableColumnEdad;
	@FXML
	private TableColumn<Animal, String> tableColumnEspecie;
	@FXML
	private TableColumn<Animal, String> tableColumnFecha;
	@FXML
	private TableColumn<Animal, String> tableColumnNombre;
	@FXML
	private TableColumn<Animal, String> tableColumnObservaciones;
	@FXML
	private TableColumn<Animal, Float> tableColumnPeso;
	@FXML
	private TableColumn<Animal, String> tableColumnRaza;
	@FXML
	private TableColumn<Animal, String> tableColumnSexo;

	@FXML
	private TableView<Animal> tableVeterinario;

	@FXML
	private Button buttonAgregarPersona;
	@FXML
	private Button buttonEliminarPersona;
	@FXML
	private Button buttonModificarPersona;

	@FXML
	private TextField textFieldFiltro;

	private ObservableList<Animal> animalExistentes;
	private ObservableList<Animal> filteredList;
	private Animal animalSeleccionado;

	private VeterinarioDao veterinarioDao = new VeterinarioDao();

	/**
	 * Inicializa la tabla y carga los datos desde la base de datos.
	 */
	@FXML
	public void initialize() {
		cargarDatos();

		// Configuración de las columnas de la tabla
		tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tableColumnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
		tableColumnRaza.setCellValueFactory(new PropertyValueFactory<>("raza"));
		tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		tableColumnEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
		tableColumnPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		tableColumnObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
		tableColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

		tableVeterinario.setItems(animalExistentes);

		// Listener para el filtro en la tabla
		textFieldFiltro.textProperty().addListener((observable, oldValue, newValue) -> filtrarAnimal(newValue));
	}

	/**
	 * Carga los datos de los animales desde la base de datos.
	 */
	private void cargarDatos() {
		animalExistentes = FXCollections.observableArrayList(veterinarioDao.cargarAnimales());
		tableVeterinario.setItems(animalExistentes);
	}

	/**
	 * Agrega un nuevo animal a la base de datos y lo muestra en la tabla.
	 *
	 * @param event Evento de acción del botón de agregar.
	 */
	@FXML
	void actionAgregarAnimal(ActionEvent event) {
		SModal modal = new SModal();
		SModalController controlador = modal.getController();
		Animal nuevoAnimal = controlador.getAnimal();  // Obtener el animal del modal

		// Verificar si el animal es null, lo que indica que no se ha agregado ningún animal
		if (nuevoAnimal == null) {
			return; // No hacer nada si no se ha creado un nuevo animal
		}

		// Verificar si el animal ya existe en la lista
		if (animalExistentes.contains(nuevoAnimal)) {
			mostrarAlerta(AlertType.ERROR, "Ya existe este animal en la tabla", "ERROR");
		} else {
			// Si el animal no existe, agregarlo a la base de datos y a la lista
			veterinarioDao.nuevoVeterinario(nuevoAnimal);
			animalExistentes.add(nuevoAnimal);
			modal.close();  // Cerrar el modal después de agregar el animal
		}
	}


	/**
	 * Elimina el animal seleccionado de la base de datos y la tabla.
	 *
	 * @param event Evento de acción del botón de eliminar.
	 */
	@FXML
	void actionEliminarAnimal(ActionEvent event) {

		Animal animal = tableVeterinario.getSelectionModel().getSelectedItem();
		if (animal == null) {
			mostrarAlerta(AlertType.ERROR, "No se ha seleccionado ningún animal", "ERROR");
		} else if (confirmarBorrado(animal)) {
			veterinarioDao.borrarVeterinario(animal);
			animalExistentes.remove(animal);
		}
	}

	/**
	 * Modifica el animal seleccionado en la base de datos y actualiza la tabla.
	 *
	 * @param event Evento de acción del botón de modificar.
	 */
	@FXML
	void actionModificarAnimal(ActionEvent event) {
		animalSeleccionado = tableVeterinario.getSelectionModel().getSelectedItem();
		if (animalSeleccionado == null) {
			mostrarAlerta(AlertType.ERROR, "No se ha seleccionado ningún animal", "ERROR");
			return;
		}

		// Crear una instancia de la ventana modal
		SModal modal = new SModal(animalSeleccionado);
		SModalController controlador = modal.getController();

		// Cargar los datos del animal seleccionado en la ventana modal
		controlador.cargarDatosAnimal(animalSeleccionado);

		// Mostrar la ventana modal después de cargar los datos
		//modal.showAndWait();

		// Obtener el animal modificado después de cerrar la ventana modal
		Animal animalModificado = controlador.getAnimal();
		if (animalModificado != null) {
			// Actualizar el animal en la base de datos y la tabla
			veterinarioDao.modificarVeterinario(animalSeleccionado, animalModificado);
			animalExistentes.set(animalExistentes.indexOf(animalSeleccionado), animalModificado);
		}
	}



	/**
	 * Filtra la lista de animales en la tabla según el texto ingresado en el campo de búsqueda.
	 *
	 * @param filtro El texto de filtro.
	 */
	private void filtrarAnimal(String filtro) {
		filteredList = animalExistentes.filtered(v -> v.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
				v.getEspecie().toLowerCase().contains(filtro.toLowerCase()) ||
				v.getRaza().toLowerCase().contains(filtro.toLowerCase()) ||
				v.getObservaciones().toLowerCase().contains(filtro.toLowerCase()));

		tableVeterinario.setItems(filteredList);
	}

	/**
	 * Muestra una alerta con el tipo, mensaje y título especificados.
	 *
	 * @param tipo Tipo de alerta.
	 * @param mensaje Mensaje a mostrar.
	 * @param titulo Título de la alerta.
	 */
	private void mostrarAlerta(AlertType tipo, String mensaje, String titulo) {
		Alert alerta = new Alert(tipo);
		alerta.setContentText(mensaje);
		alerta.setTitle(titulo);
		alerta.showAndWait();
	}

	/**
	 * Muestra una confirmación para eliminar el animal.
	 *
	 * @param animal El animal a eliminar.
	 * @return true si se confirma la eliminación, false en caso contrario.
	 */
	private boolean confirmarBorrado(Animal animal) {
		Alert confirmacion = new Alert(AlertType.CONFIRMATION);
		confirmacion.setTitle("Confirmar Eliminación");
		confirmacion.setContentText("¿Está seguro de que desea eliminar el animal " + animal.getNombre() + "?");
		return confirmacion.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
	}
}
