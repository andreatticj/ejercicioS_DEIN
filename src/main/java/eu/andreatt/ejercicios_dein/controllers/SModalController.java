package eu.andreatt.ejercicios_dein.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Veterinario;

public class SModalController {

	/** VARIABLES */
    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonGuardar;

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
    
    public Veterinario veterinario;

	/** EVENTO - AL PULSAR CANCELAR */
    @FXML
    void actionCancelar(ActionEvent event) {
    	//Cerrar ventana modal
	    Stage stage = (Stage) buttonCancelar.getScene().getWindow();
	    stage.close();
    }

    /** EVENTO - AL PULSAR GUARDAR */
    @FXML
    void actionGuardar(ActionEvent event) {
    	//Validar datos introducidos
    	String errores=validarDatos();
    	
    	//Mostrar alerta y añadir animal si es válida
    	if(!errores.equals("")) {
    		Alert alerta = generarVentana(AlertType.ERROR, errores, "ERROR");
    		alerta.show();
    	}else {
    		veterinario = new Veterinario(textFieldNombre.getText(), textFieldEspecie.getText(), textFieldRaza.getText(), textFieldSexo.getText(), Integer.parseInt(textFieldEdad.getText()), Float.parseFloat(textFieldPeso.getText()), textFieldObservaciones.getText(), textFieldFecha.getText());
        	System.out.println(veterinario);
        	//Cerrar ventana
    	    Stage stage = (Stage) buttonCancelar.getScene().getWindow();
    	    stage.close();
    	}
    }
    
    /** VALIDAR TEXTFIELDS DE ANIMAL */
    private String validarDatos() {
    	String errores="";
    	
    	//Validar nombre
    	if(textFieldNombre.getText().trim().length()==0) {
    		errores+="Se debe escribir el nombre\n";
    	}
    	
    	//Validar especie
    	if(textFieldEspecie.getText().trim().length()==0) {
    		errores+="Se debe escribir la especia\n";
    	}
    	
    	//Validar raza
    	if(textFieldRaza.getText().trim().length()==0) {
    		errores+="Se debe escribir la raza\n";
    	}
    	
    	//Validar sexo
    	if(textFieldSexo.getText().trim().length()==0) {
    		errores+="Se debe escribir el sexo\n";
    	}
    	
    	//Validar fecha
    	if(textFieldFecha.getText().trim().length()==0) {
    		errores+="Se debe escribir una fecha\n";
    	}
    		
    	//Validad edad
    	if(textFieldEdad.getText().trim().length()==0) {
    		errores+="Se debe escribir una edad\n";
    	}else {
        	try {
        		Integer.parseInt(textFieldEdad.getText());
        	}catch (NumberFormatException e) {
        		errores+="La edad debe ser un número\n";
        	}
    	}
    	
    	//Validad peso
    	if(textFieldPeso.getText().trim().length()==0) {
    		errores+="Se debe escribir el peso\n";
    	}else {
        	try {
        		Float.parseFloat(textFieldPeso.getText());
        	}catch (NumberFormatException e) {
        		errores+="El peso debe ser un número\n";
        	}
    	}
    	
    	return errores;	
    }

    /** GENERAR VENTANA DE ALERTA */
    private Alert generarVentana(AlertType tipoDeAlerta, String mensaje, String title) {
		Alert alerta = new Alert(tipoDeAlerta);
		alerta.setContentText(mensaje);
		alerta.setHeaderText(null);
		alerta.setTitle(title);
		return alerta;
    }
    
    /** GETTERS Y SETTERS */
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