package eu.andreatt.ejercicios_dein.dao;

import eu.andreatt.ejercicios_dein.bbdd.ConexionBD;
import eu.andreatt.ejercicios_dein.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeterinarioDao {
	private ConexionBD conexion;

	/** CARGAR LOS VETERINARIOS EXISTENTES EN BASE DE DATOS */
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
				String fechaPrimeraConsulta = rs.getString("fecha_primera_consulta");
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

	/** INSERTAR UN VETERINARIO EN LA BASE DE DATOS */
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
			pstmt.setString(9, p.getFecha());
			pstmt.setBlob(9, p.getImagen());

			pstmt.executeUpdate();
			conexion.closeConnection();
			return true; // Retornar verdadero si la inserción fue exitosa

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Retornar falso si hubo un error
	}

	/** BORRAR UN VETERINARIO EN LA BASE DE DATOS */
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

	/** ACTUALIZAR UN VETERINARIO EN LA BASE DE DATOS */
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
			pstmt.setString(8, nuevoAnimal.getFecha());

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
	/** OBTENER EL ID DEL VETERINARIO SOLICITADO */
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

	/** OBTENER EL ID MÁS GRANDE DE LOS VETERINARIOS EN BASE DE DATOS */
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
}
