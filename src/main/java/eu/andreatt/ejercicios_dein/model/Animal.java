package eu.andreatt.ejercicios_dein.model;

import java.sql.Blob;
import java.util.Objects;

/** CLASE VETERINARIO PARA EL EJERCICIO (S) */
public class Animal {

	/** VARIABLES */
	private String nombre, especie, raza, sexo, observaciones, fecha;
	private int id, edad;
	private float peso;
	private Blob imagen;

	/** CONSTRUCTOR */
	public Animal(String nombre, String especie, String raza, String sexo, int edad, float peso, String observaciones, String fecha, Blob imagen) {
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

	/** GETTERS Y SETTERS */
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public Blob getImagen() {
		return imagen; // Getter para la imagen en formato byte[]
	}

	public void setImagen(Blob magen) {
		this.imagen = imagen; // Setter para la imagen en formato byte[]
	}

	/** EQUALS Y HASHCODE */
	@Override
	public int hashCode() {
		return Objects.hash(edad, especie, fecha, id, nombre, observaciones, peso, raza, sexo, imagen);
	}

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

	/** TO STRING */
	@Override
	public String toString() {
		return nombre + " - " + raza;
	}
}