package eu.andreatt.ejercicios_dein.model;

import javafx.scene.control.DatePicker;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase que representa un animal en el contexto del ejercicio (S).
 * Contiene los atributos, métodos y operaciones asociados a un animal, como nombre, especie, raza, sexo, etc.
 */
public class Animal {

	/** Variables que almacenan la información de un animal. */
	private String nombre, especie, raza, sexo, observaciones;
	private LocalDate fecha;
	private int id, edad;
	private float peso;
	private Blob imagen;

	/**
	 * Constructor que inicializa un objeto Animal con los valores proporcionados.
	 *
	 * @param nombre Nombre del animal.
	 * @param especie Especie del animal.
	 * @param raza Raza del animal.
	 * @param sexo Sexo del animal.
	 * @param edad Edad del animal.
	 * @param peso Peso del animal.
	 * @param observaciones Observaciones sobre el animal.
	 * @param fecha Fecha de nacimiento o algún otro tipo de fecha relevante del animal.
	 * @param imagen Imagen del animal en formato {@link Blob}.
	 */
	public Animal(String nombre, String especie, String raza, String sexo, int edad, float peso, String observaciones, LocalDate fecha, Blob imagen) {
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.sexo = sexo;
		this.edad = edad;
		this.peso = peso;
		this.observaciones = observaciones;
		this.fecha = fecha;
		this.imagen = imagen; // Asignar la imagen
	}

	/** Métodos getter y setter para acceder y modificar los valores de las variables. */

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	/**
	 * Obtiene la imagen del animal en formato {@link Blob}.
	 *
	 * @return La imagen del animal en formato {@link Blob}.
	 */
	public Blob getImagen() {
		return imagen; // Getter para la imagen en formato byte[]
	}

	/**
	 * Establece la imagen del animal en formato {@link Blob}.
	 *
	 * @param imagen La imagen del animal en formato {@link Blob}.
	 */
	public void setImagen(Blob imagen) {
		this.imagen = imagen; // Setter para la imagen en formato byte[]
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalDate getFecha() {
		return fecha;
	}


	/**
	 * Metodo para generar un código hash único para el objeto {@link Animal}.
	 *
	 * @return Un valor entero que representa el código hash del objeto {@link Animal}.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(edad, especie, fecha, id, nombre, observaciones, peso, raza, sexo, imagen);
	}

	/**
	 * Metodo para comparar si dos objetos {@link Animal} son iguales.
	 *
	 * @param obj El objeto a comparar con el actual.
	 * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Animal other = (Animal) obj;
		return edad == other.edad &&
				Float.compare(other.peso, peso) == 0 &&
				id == other.id &&
				Objects.equals(nombre, other.nombre) &&
				Objects.equals(especie, other.especie) &&
				Objects.equals(raza, other.raza) &&
				Objects.equals(sexo, other.sexo) &&
				Objects.equals(observaciones, other.observaciones) &&
				Objects.equals(fecha, other.fecha) &&
				Objects.equals(imagen, other.imagen);
	}

	/**
	 * Metodo que devuelve una representación en forma de cadena del objeto {@link Animal}.
	 *
	 * @return Una cadena representando el animal con su nombre y raza.
	 */
	@Override
	public String toString() {
		return nombre + " - " + raza;
	}
}
