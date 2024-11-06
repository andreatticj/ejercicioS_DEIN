package eu.andreatt.ejercicios_dein.dao;

import eu.andreatt.ejercicios_dein.bbdd.ConexionBD;
import eu.andreatt.ejercicios_dein.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;

public class VeterinarioDao {
	private ConexionBD conexion;

	/**
	 * Carga todos los veterinarios existentes en la base de datos y los devuelve como una lista observable de objetos Animal.
	 *
	 * @return Una lista observable de animales.
	 */
	public ObservableList<Animal> cargarAnimales() {
		ObservableList<Animal> animals = FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			String consulta = "SELECT * FROM Veterinario";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String nombreAnimal = rs.getString("nombre");
				String especieAnimal = rs.getString("especie");
				String razaAnimal = rs.getString("raza");
				String sexoAnimal = rs.getString("sexo");
				int edadAnimal = rs.getInt("edad");
				float pesoAnimal = rs.getFloat("peso");
				String observacionesAnimal = rs.getString("observaciones");
				LocalDate fechaPrimeraConsulta = rs.getDate("fecha_primera_consulta").toLocalDate();
				Blob foto = rs.getBlob("imagen");

				// Crear objeto Veterinario con el Blob de la imagen
				animals.add(new Animal(nombreAnimal, especieAnimal, razaAnimal, sexoAnimal, edadAnimal, pesoAnimal, observacionesAnimal, fechaPrimeraConsulta, foto));
			}
			rs.close();
			conexion.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return animals;
	}

	/**
	 * Inserta un nuevo veterinario en la base de datos.
	 * Si el veterinario ya existe, la inserción no se realiza.
	 *
	 * @param p El objeto Animal que representa al veterinario a insertar.
	 * @return true si la inserción fue exitosa, false si el veterinario ya existe.
	 */
	public boolean nuevoVeterinario(Animal p) {
		// Validar si el veterinario ya existe
		ObservableList<Animal> animals = cargarAnimales();
		if (animals.contains(p)) {
			return false;
		}

		try {
			conexion = new ConexionBD();
			int id = dameMaxID() + 1; // Obtener un nuevo ID

			String consulta = "INSERT INTO Veterinario (id, nombre, especie, raza, sexo, edad, peso, observaciones, fecha_primera_consulta, imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			pstmt.setInt(1, id);
			pstmt.setString(2, p.getNombre());
			pstmt.setString(3, p.getEspecie());
			pstmt.setString(4, p.getRaza());
			pstmt.setString(5, p.getSexo());
			pstmt.setInt(6, p.getEdad());
			pstmt.setFloat(7, p.getPeso());
			pstmt.setString(8, p.getObservaciones());
			pstmt.setDate(9, Date.valueOf(p.getFecha()));
			pstmt.setBlob(10, p.getImagen());

			pstmt.executeUpdate();
			conexion.closeConnection();
			return true; // Retornar verdadero si la inserción fue exitosa

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Retornar falso si hubo un error
	}

	/**
	 * Elimina un veterinario de la base de datos.
	 *
	 * @param p El objeto Animal que representa al veterinario a eliminar.
	 */
	public void borrarVeterinario(Animal p) {
		try {
			conexion = new ConexionBD();
			String consulta = "DELETE FROM Veterinario WHERE id = ?";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			pstmt.setInt(1, dameIDVeterinario(p));
			pstmt.executeUpdate();
			conexion.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza la información de un veterinario en la base de datos.
	 *
	 * @param antiguoAnimal El objeto Animal que contiene los datos antiguos.
	 * @param nuevoAnimal El objeto Animal que contiene los nuevos datos a actualizar.
	 */
	public void modificarVeterinario(Animal antiguoAnimal, Animal nuevoAnimal) {
		try {
			conexion = new ConexionBD();
			String consulta = "UPDATE Veterinario SET nombre = ?, especie = ?, raza = ?, sexo = ?, edad = ?, peso = ?, observaciones = ?, fecha_primera_consulta = ?, imagen = ? WHERE id = ?";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

			pstmt.setString(1, nuevoAnimal.getNombre());
			pstmt.setString(2, nuevoAnimal.getEspecie());
			pstmt.setString(3, nuevoAnimal.getRaza());
			pstmt.setString(4, nuevoAnimal.getSexo());
			pstmt.setInt(5, nuevoAnimal.getEdad());
			pstmt.setFloat(6, nuevoAnimal.getPeso());
			pstmt.setString(7, nuevoAnimal.getObservaciones());
			pstmt.setDate(8, Date.valueOf(nuevoAnimal.getFecha()));

			// Verificar si la imagen es null antes de intentar obtener el BinaryStream
			Blob imagenBlob = nuevoAnimal.getImagen();
			if (imagenBlob != null) {
				InputStream imagenStream = imagenBlob.getBinaryStream();
				pstmt.setBlob(9, imagenStream);
			} else {
				pstmt.setNull(9, java.sql.Types.BLOB);  // Establecer NULL si la imagen es null
			}

			pstmt.setInt(10, dameIDVeterinario(antiguoAnimal));
			pstmt.executeUpdate();
			conexion.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el ID de un veterinario basado en su nombre, raza y edad.
	 *
	 * @param p El objeto Animal que contiene los datos para buscar el ID.
	 * @return El ID del veterinario, o -1 si no se encuentra.
	 */
	public int dameIDVeterinario(Animal p) {
		try {
			conexion = new ConexionBD();
			String consulta = "SELECT id FROM Veterinario WHERE nombre = ? AND raza = ? AND edad = ?";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getRaza());
			pstmt.setInt(3, p.getEdad());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("id");
			}
			rs.close();
			conexion.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Retornar -1 si no se encontró
	}

	/**
	 * Obtiene el ID más grande de los veterinarios en la base de datos.
	 *
	 * @return El ID más grande, o -1 si no se encuentra.
	 */
	public int dameMaxID() {
		try {
			conexion = new ConexionBD();
			String consulta = "SELECT MAX(id) AS ID FROM Veterinario";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("ID");
			}
			rs.close();
			conexion.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Retornar -1 si no se encontró
	}

	/**
	 * Convierte un archivo en un Blob para poder almacenarlo en la base de datos.
	 *
	 * @param file El archivo a convertir en Blob.
	 * @return El Blob que representa el archivo; null si ocurre un error.
	 */
	public Blob convertFileToBlob(File file) {
		Blob blob = null;

		try {
			conexion = new ConexionBD(); // Establece la conexión a la base de datos

			// Crear el Blob
			blob = conexion.getConexion().createBlob();

			// Escribir los bytes del archivo en el Blob
			try (FileInputStream inputStream = new FileInputStream(file);
				 var outputStream = blob.setBinaryStream(1)) {

				byte[] buffer = new byte[1024];
				int bytesRead;

				// Lee el archivo y escribe en el Blob
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}

			conexion.closeConnection(); // Cierra la conexión a la base de datos
		} catch (SQLException e) {
			System.err.println("Error de SQL: " + e.getMessage()); // Manejo de errores de SQL
		} catch (IOException e) {
			System.err.println("Error de IO: " + e.getMessage()); // Manejo de errores de IO
		}

		return blob; // Devuelve el Blob creado
	}
}