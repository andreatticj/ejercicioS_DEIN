package eu.andreatt.ejercicios_dein.model;

import java.util.Objects;

/** CLASE PERSONA PARA LOS EJERCICIOS (B-I) */
public class Persona {

	/** VARIABLES */
	private String nombre, apellidos;
	private int edad;
	
	/** CONSTRUCTOR */
	public Persona(String nombre, String apellidos, int edad) {
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.edad=edad;
	}

	/** GETTERS Y SETTERS */
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	/** EQUALS Y HASHCODE */
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, edad, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(apellidos, other.apellidos) && edad == other.edad && Objects.equals(nombre, other.nombre);
	}
	
	/** TO STRING */
	public String toString() {
		return nombre+" "+apellidos+", "+edad+" años";
	}
}