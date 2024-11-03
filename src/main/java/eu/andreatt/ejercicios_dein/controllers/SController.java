package eu.andreatt.ejercicios_dein.application.controllers;

import application.SModal;
import dao.VeterinarioDao;
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
import model.Persona;
import model.Veterinario;

public class SController {

	/** VARIABLES */

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

    @FXML
    private TableView<Veterinario> tableVeterinario;
    
    @FXML
    private Button buttonAgregarPersona;

    @FXML
    private Button buttonEliminarPersona;

    @FXML
    private Button buttonModificarPersona;

    @FXML
    private TextField textFieldFiltro;
    
    private ObservableList<Veterinario>veterinarioExistentes;
    
    ObservableList<Veterinario> filteredList;
    
    private Veterinario veterinario;
    
    private SModalController controlador;
    
    private VeterinarioDao veterinarioDao;
    
    /** INITIALIZE - INICIALIZAR CARGA DE DATOS */
    @FXML
    public void initialize() {
    	
    	//Veterinario en BBDD
       	veterinarioDao = new VeterinarioDao();
    	
    	//Coleccion observable de animales de la tabla
       	veterinarioExistentes = FXCollections.observableArrayList();
       	veterinarioExistentes = veterinarioDao.cargarVeterinario();
		
		//Instanciar celdas de la tabla para mostrar la información de los animales introducidos
		tableColumnNombre.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("nombre"));
		tableColumnEspecie.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("especie"));
		tableColumnRaza.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("raza"));
		tableColumnSexo.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("sexo"));
		tableColumnEdad.setCellValueFactory(new PropertyValueFactory<Veterinario, Integer>("edad"));
		tableColumnPeso.setCellValueFactory(new PropertyValueFactory<Veterinario, Float>("peso"));
		tableColumnObservaciones.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("observaciones"));
		tableColumnFecha.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("fecha"));
		
		tableVeterinario.setItems(veterinarioExistentes);
		
		textFieldFiltro.setOnAction(event -> {
		    String filter = textFieldFiltro.getText().toLowerCase();
		    filteredList = veterinarioExistentes.filtered(animal -> animal.getNombre().toLowerCase().contains(filter));
		    tableVeterinario.setItems(filteredList);});
    }

    /** EVENTO - AL PULSAR AGREGAR */
    @FXML
    void actionAgregarAnimal(ActionEvent event) {
    	SModal modal = new SModal();
    	
    	//Crear animal con los datos recibidos
        controlador = modal.getController();
        Veterinario nuevoVeterinario = controlador.veterinario;
        
        if (nuevoVeterinario!=null) {
        	System.out.println("VETERINARIO IS NULL: "+veterinario);
    		//Buscar que el animal no existe en la tabla
    		if(veterinarioExistentes.contains(nuevoVeterinario)) {
        		Alert alerta = generarVentana(AlertType.ERROR, "Ya existe este animal en la tabla", "ERROR");
        		alerta.show();
    		}else {
    			veterinarioDao.nuevoVeterinario(nuevoVeterinario);
    			veterinarioExistentes.add(nuevoVeterinario);
    			        			
    			//Actualizar datos en la tabla
    			tableVeterinario.refresh();
    			
        	    //Mostrar mensaje de alerta
        		Alert alerta = generarVentana(AlertType.INFORMATION, "Se ha añadido un animal", "INFO");
        		alerta.show();
    		}
    	}
    }

    /** EVENTO - AL PULSAR ELIMINAR */
    @FXML
    void actionEliminarAnimal(ActionEvent event) {
    	//Persona seleccionada
    	Veterinario itemSeleccionado = tableVeterinario.getSelectionModel().getSelectedItem();
    	System.out.println(itemSeleccionado);
    	
    	//Guardar todos los animales de la tabla
    	ObservableList<Veterinario> animalesEnLista = FXCollections.observableArrayList();
        animalesEnLista.addAll(tableVeterinario.getItems());
    	
    	if(itemSeleccionado!=null) {
    		veterinarioDao.borrarVeterinario(itemSeleccionado);
    		animalesEnLista.remove(itemSeleccionado);
    		
    		//Actualizar datos en la tabla
    		tableVeterinario.setItems(animalesEnLista);
    		tableVeterinario.refresh();
    		
    		//Mostrar alerta
    		Alert alerta = generarVentana(AlertType.INFORMATION, "Se ha borrado un animal", "INFO");
    		alerta.show();   
    	}else {
    		Alert alerta = generarVentana(AlertType.ERROR, "NO se ha seleccionado ningún animal", "ERROR");
    		alerta.show();  
    	}
    }

    /** EVENTO - AL PULSAR MODIFICAR */
    @FXML
    void actionModificarAnimal(ActionEvent event) {
    	if(veterinario!=null) {
    	    //Generar ventana modal con los datos del animal seleccionado
    	  	SModal modal = new SModal(veterinario.getNombre(), veterinario.getEspecie(), veterinario.getRaza(), veterinario.getSexo(), veterinario.getEdad(), veterinario.getPeso(), veterinario.getObservaciones(), veterinario.getFecha()); 
    	  	
            controlador = modal.getController();
            Veterinario nuevoVeterinario = controlador.veterinario;
            
            if(veterinario!=null) {
	        	veterinarioDao.modificarVeterinario(veterinario, nuevoVeterinario);
	    		veterinarioExistentes.set(veterinarioExistentes.indexOf(veterinario), nuevoVeterinario);

				//Actualizar datos en la tabla
				tableVeterinario.refresh();
        	}
    	}else {
        	Alert alerta = generarVentana(AlertType.ERROR, "No se ha seleccionado ningún animal en la tabla", "ERROR");
        	alerta.show();
    	}
    }

    /** EVENTO - AL PULSAR LA TABLA */
    @SuppressWarnings("unchecked")
	@FXML
    void actionTablaPulsada(MouseEvent event) {
    	int selectedRow = -1;
        int selectedColumn = -1;
            	
    	try {
    		//Recuperar celda actual
    		TableView<Veterinario> source = (TableView<Veterinario>) event.getSource();
    		TablePosition<Persona, String> pos = source.getSelectionModel().getSelectedCells().get(0);
            
            selectedRow = pos.getRow();
            selectedColumn = pos.getColumn();
                                 
            Object cellData = source.getColumns().get(selectedColumn).getCellData(selectedRow);
            
            //Generar animal seleccionado
            veterinario = new Veterinario(
            		String.valueOf(source.getColumns().get(0).getCellData(selectedRow)),
            		String.valueOf(source.getColumns().get(1).getCellData(selectedRow)),
            		String.valueOf(source.getColumns().get(2).getCellData(selectedRow)),
            		String.valueOf(source.getColumns().get(3).getCellData(selectedRow)),
            		Integer.parseInt(String.valueOf(source.getColumns().get(4).getCellData(selectedRow))),
            		Float.parseFloat(String.valueOf(source.getColumns().get(5).getCellData(selectedRow))),
					String.valueOf(source.getColumns().get(6).getCellData(selectedRow)),
            		String.valueOf(source.getColumns().get(7).getCellData(selectedRow)));
                    
            System.out.println("Veterinario ACTUAL "+veterinario);
            
            //DEBUG - Visualizar fila y celda actual + valor del click
            System.out.println("Fila: " + selectedRow + ", Columna: " + selectedColumn);
            System.out.println(cellData.toString());
            
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /** GENERAR VENTANA DE ALERTA */
    private Alert generarVentana(AlertType tipoDeAlerta, String mensaje, String title) {
		Alert alerta = new Alert(tipoDeAlerta);
		alerta.setContentText(mensaje);
		alerta.setHeaderText(null);
		alerta.setTitle(title);
		return alerta;
    }  
}