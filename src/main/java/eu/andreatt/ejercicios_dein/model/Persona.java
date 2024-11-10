package eu.andreatt.ejercicios_dein.model;

import java.util.Objects;

/**
 * Clase que representa una persona con su nombre, apellidos y edad.
 */
public class Persona {

	/** Variables que almacenan el nombre, apellidos y edad de la persona. */
	private String nombre, apellidos;
	private int edad;

	/**
	 * Constructor para crear una persona con su nombre, apellidos y edad.
	 *
	 * @param nombre El nombre de la persona.
	 * @param apellidos Los apellidos de la persona.
	 * @param edad La edad de la persona.
	 */
	public Persona(String nombre, String apellidos, int edad) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}

	/**
	 * Devuelve el nombre de la persona.
	 *
	 * @return El nombre de la persona.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de la persona.
	 *
	 * @param nombre El nuevo nombre de la persona.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve los apellidos de la persona.
	 *
	 * @return Los apellidos de la persona.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece los apellidos de la persona.
	 *
	 * @param apellidos Los nuevos apellidos de la persona.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve la edad de la persona.
	 *
	 * @return La edad de la persona.
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * Establece la edad de la persona.
	 *
	 * @param edad La nueva edad de la persona.
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/**
	 * Calcula el código hash de la persona, utilizando su nombre, apellidos y edad.
	 *
	 * @return El código hash de la persona.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, edad, nombre);
	}

	/**
	 * Compara dos personas para ver si son iguales basándose en su nombre, apellidos y edad.
	 *
	 * @param obj El objeto con el que se va a comparar.
	 * @return true si las dos personas son iguales, false si no lo son.
	 */
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

	/**
	 * Devuelve una representación en forma de cadena de la persona, con su nombre, apellidos y edad.
	 *
	 * @return Una cadena que describe la persona.
	 */
	public String toString() {
		return nombre + " " + apellidos + ", " + edad + " años";
	}
}
