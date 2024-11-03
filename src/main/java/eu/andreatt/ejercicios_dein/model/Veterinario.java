package eu.andreatt.ejercicios_dein.model;

import java.util.Objects;

import javafx.scene.image.ImageView;

/** CLASE VETERINARIO PARA EL EJERCICIO (S) */
public class Veterinario {

	/** VARIABLES */
	private String nombre, especie, raza, sexo, observaciones, fecha;
	private int id, edad;
	private float peso;
	private ImageView foto;
	
	/** CONSTRUCTOR */
	public Veterinario(String nombre, String especie, String raza, String sexo, int edad, float peso, String observaciones, String fecha) {
		this.nombre=nombre;
		this.especie=especie;
		this.raza=raza;
		this.sexo=sexo;
		this.edad=edad;
		this.peso=peso;
		this.observaciones=observaciones;
		this.fecha=fecha;
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

	public ImageView getFoto() {
		return foto;
	}

	public void setFoto(ImageView foto) {
		this.foto = foto;
	}
	
	/** EQUALS Y HASHCODE */
	@Override
	public int hashCode() {
		return Objects.hash(edad, especie, fecha, foto, id, nombre, observaciones, peso, raza, sexo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veterinario other = (Veterinario) obj;
		return edad == other.edad && Objects.equals(especie, other.especie) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(foto, other.foto) && id == other.id && Objects.equals(nombre, other.nombre)
				&& Objects.equals(observaciones, other.observaciones)
				&& Float.floatToIntBits(peso) == Float.floatToIntBits(other.peso) && Objects.equals(raza, other.raza)
				&& Objects.equals(sexo, other.sexo);
	}

	/** TO STRING */
	public String toString() {
		return nombre+" - "+raza;
	}
}