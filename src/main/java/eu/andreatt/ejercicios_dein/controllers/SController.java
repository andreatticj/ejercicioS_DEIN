package eu.andreatt.ejercicios_dein.controllers;

import eu.andreatt.ejercicios_dein.application.SModal;
import eu.andreatt.ejercicios_dein.dao.VeterinarioDao;
import eu.andreatt.ejercicios_dein.model.Veterinario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Controlador para la gestión de veterinarios en la aplicación.
 *
 * Proporciona funcionalidades para agregar, eliminar y modificar registros
 * de veterinarios en una tabla de la interfaz gráfica.
 */
public class SController {

	/** Columnas de la tabla que muestran la información de cada veterinario */
	@FXML
	private TableColumn<Veterinario, Integer> tableColumnEdad;
	@FXML
	private TableColumn<Veterinario, String> tableColumnEspecie;
	@FXML
	private TableColumn<Veterinario, String> tableColumnFecha;
	@FXML
	private TableColumn<Veterinario, ImageView> tableColumnFoto;
	@FXML
	private TableColumn<Veterinario, String> tableColumnNombre;
	@FXML
	private TableColumn<Veterinario, String> tableColumnObservaciones;
	@FXML
	private TableColumn<Veterinario, Float> tableColumnPeso;
	@FXML
	private TableColumn<Veterinario, String> tableColumnRaza;
	@FXML
	private TableColumn<Veterinario, String> tableColumnSexo;

	/** Tabla que contiene la lista de veterinarios */
	@FXML
	private TableView<Veterinario> tableVeterinario;

	/** Botones para agregar, eliminar y modificar veterinarios */
	@FXML
	private Button buttonAgregarPersona;
	@FXML
	private Button buttonEliminarPersona;
	@FXML
	private Button buttonModificarPersona;

	/** Campo de texto para filtrar veterinarios */
	@FXML
	private TextField textFieldFiltro;

	/** Lista de veterinarios existentes cargados desde la base de datos */
	private ObservableList<Veterinario> veterinarioExistentes;

	/** Lista de veterinarios filtrada en base al campo de búsqueda */
	private ObservableList<Veterinario> filteredList;

	/** Veterinario actualmente seleccionado */
	private Veterinario veterinario;

	/** Controlador del modal para ingresar datos de veterinarios */
	private SModalController controlador;

	/** Objeto de acceso a datos para la clase Veterinario */
	private VeterinarioDao veterinarioDao;

	/**
	 * Inicializa la tabla y carga los datos desde la base de datos.
	 */
	@FXML
	public void initialize() {
		veterinarioDao = new VeterinarioDao();
		veterinarioExistentes = FXCollections.observableArrayList(veterinarioDao.cargarVeterinario());

		// Configuración de las celdas de la tabla para mostrar los datos
		tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tableColumnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
		tableColumnRaza.setCellValueFactory(new PropertyValueFactory<>("raza"));
		tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		tableColumnEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
		tableColumnPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		tableColumnObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
		tableColumnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

		tableVeterinario.setItems(veterinarioExistentes);

		// Configuración del filtro de búsqueda en tiempo real
		textFieldFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
			String filter = newValue.toLowerCase();

			filteredList = veterinarioExistentes.filtered(animal ->
					animal.getNombre().toLowerCase().contains(filter) ||
							animal.getEspecie().toLowerCase().contains(filter) ||
							animal.getRaza().toLowerCase().contains(filter) ||
							animal.getObservaciones().toLowerCase().contains(filter)
			);

			tableVeterinario.setItems(filteredList);
		});
	}

	/**
	 * Agrega un nuevo veterinario a la base de datos y lo muestra en la tabla.
	 *
	 * @param event Evento de acción del botón de agregar.
	 */
	@FXML
	void actionAgregarAnimal(ActionEvent event) {
		SModal modal = new SModal();
		controlador = modal.getController();
		Veterinario nuevoVeterinario = controlador.veterinario;

		if (nuevoVeterinario != null) {
			if (veterinarioExistentes.contains(nuevoVeterinario)) {
				Alert alerta = generarVentana(AlertType.ERROR, "Ya existe este animal en la tabla", "ERROR");
				alerta.show();
			} else {
				veterinarioDao.nuevoVeterinario(nuevoVeterinario);
				veterinarioExistentes.add(nuevoVeterinario);
				tableVeterinario.refresh();

				Alert alerta = generarVentana(AlertType.INFORMATION, "Se ha añadido un animal", "INFO");
				alerta.show();
			}
		}
	}

	/**
	 * Elimina el veterinario seleccionado de la base de datos y de la tabla.
	 *
	 * @param event Evento de acción del botón de eliminar.
	 */
	@FXML
	void actionEliminarAnimal(ActionEvent event) {
		Veterinario itemSeleccionado = tableVeterinario.getSelectionModel().getSelectedItem();

		if (itemSeleccionado != null) {
			veterinarioDao.borrarVeterinario(itemSeleccionado);
			veterinarioExistentes.remove(itemSeleccionado);
			tableVeterinario.refresh();

			Alert alerta = generarVentana(AlertType.INFORMATION, "Se ha borrado un animal", "INFO");
			alerta.show();
		} else {
			Alert alerta = generarVentana(AlertType.ERROR, "NO se ha seleccionado ningún animal", "ERROR");
			alerta.show();
		}
	}

	/**
	 * Modifica el veterinario seleccionado y actualiza la base de datos y la tabla.
	 *
	 * @param event Evento de acción del botón de modificar.
	 */
	@FXML
	void actionModificarAnimal(ActionEvent event) {
		if (veterinario != null) {
			SModal modal = new SModal(veterinario.getNombre(), veterinario.getEspecie(), veterinario.getRaza(),
					veterinario.getSexo(), veterinario.getEdad(), veterinario.getPeso(), veterinario.getObservaciones(),
					veterinario.getFecha());

			controlador = modal.getController();
			Veterinario nuevoVeterinario = controlador.veterinario;

			if (nuevoVeterinario != null) {
				veterinarioDao.modificarVeterinario(veterinario, nuevoVeterinario);
				veterinarioExistentes.set(veterinarioExistentes.indexOf(veterinario), nuevoVeterinario);
				tableVeterinario.refresh();
			}
		} else {
			Alert alerta = generarVentana(AlertType.ERROR, "No se ha seleccionado ningún animal en la tabla", "ERROR");
			alerta.show();
		}
	}

	/**
	 * Maneja el evento de selección de una fila en la tabla, estableciendo el
	 * veterinario seleccionado.
	 *
	 * @param event Evento de clic del ratón en la tabla.
	 */
	@FXML
	void actionTablaPulsada(MouseEvent event) {
		try {
			TableView<Veterinario> source = (TableView<Veterinario>) event.getSource();
			TablePosition<?, ?> pos = source.getSelectionModel().getSelectedCells().get(0);
			int selectedRow = pos.getRow();

			// Obtiene los datos de la fila seleccionada
			String nombre = String.valueOf(source.getColumns().get(0).getCellData(selectedRow));
			String especie = String.valueOf(source.getColumns().get(1).getCellData(selectedRow));
			String raza = String.valueOf(source.getColumns().get(2).getCellData(selectedRow));
			String sexo = String.valueOf(source.getColumns().get(3).getCellData(selectedRow));
			int edad = Integer.parseInt(String.valueOf(source.getColumns().get(4).getCellData(selectedRow)));
			float peso = Float.parseFloat(String.valueOf(source.getColumns().get(5).getCellData(selectedRow)));
			String observaciones = String.valueOf(source.getColumns().get(6).getCellData(selectedRow));
			String fecha = String.valueOf(source.getColumns().get(7).getCellData(selectedRow));

			// Obtener la imagen como byte[]
			byte[] imagen = (byte[]) source.getColumns().get(8).getCellData(selectedRow); // Suponiendo que la columna 8 contiene la imagen

			// Crear un nuevo objeto Veterinario con todos los datos
			veterinario = new Veterinario(nombre, especie, raza, sexo, edad, peso, observaciones, fecha, imagen);

			System.out.println("Veterinario ACTUAL " + veterinario);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Genera una ventana de alerta con el mensaje especificado.
	 *
	 * @param tipoDeAlerta Tipo de alerta (ERROR, INFORMATION, etc.).
	 * @param mensaje      Mensaje a mostrar en la alerta.
	 * @param title        Título de la alerta.
	 * @return Objeto Alert configurado.
	 */
	private Alert generarVentana(AlertType tipoDeAlerta, String mensaje, String title) {
		Alert alerta = new Alert(tipoDeAlerta);
		alerta.setContentText(mensaje);
		alerta.setHeaderText(null);
		alerta.setTitle(title);
		return alerta;
	}
}
