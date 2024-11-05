package eu.andreatt.ejercicios_dein.dao;

import eu.andreatt.ejercicios_dein.bbdd.ConexionBD;
import eu.andreatt.ejercicios_dein.model.Veterinario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeterinarioDao {
	private ConexionBD conexion;

	/** CARGAR LOS VETERINARIOS EXISTENTES EN BASE DE DATOS */
	public ObservableList<Veterinario> cargarVeterinario() {
		ObservableList<Veterinario> veterinarios = FXCollections.observableArrayList();
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
				byte[] imageAnimal = rs.getBytes("imagen"); // Obtener la imagen como byte array

				// Crear objeto Veterinario con la imagen
				veterinarios.add(new Veterinario(nombreAnimal, especieAnimal, razaAnimal, sexoAnimal, edadAnimal, pesoAnimal, observacionesAnimal, fechaPrimeraConsulta, imageAnimal));
			}
			rs.close();
			conexion.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return veterinarios;
	}

	/** INSERTAR UN VETERINARIO EN LA BASE DE DATOS */
	public boolean nuevoVeterinario(Veterinario p) {
		// Validar si el veterinario ya existe
		ObservableList<Veterinario> veterinarios = cargarVeterinario();
		if (veterinarios.contains(p)) {
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

			// Asignar imagen como InputStream si existe
			byte[] imageBytes = p.getImagen();
			InputStream imagenStream = (imageBytes != null) ? new ByteArrayInputStream(imageBytes) : null;
			pstmt.setBlob(10, imagenStream);

			pstmt.executeUpdate();
			conexion.closeConnection();
			return true; // Retornar verdadero si la inserción fue exitosa

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Retornar falso si hubo un error
	}

	/** BORRAR UN VETERINARIO EN LA BASE DE DATOS */
	public void borrarVeterinario(Veterinario p) {
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
	public void modificarVeterinario(Veterinario antiguoVeterinario, Veterinario nuevoVeterinario) {
		try {
			conexion = new ConexionBD();
			String consulta = "UPDATE Veterinario SET nombre = ?, especie = ?, raza = ?, sexo = ?, edad = ?, peso = ?, observaciones = ?, fecha_primera_consulta = ?, imagen = ? WHERE id = ?";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

			pstmt.setString(1, nuevoVeterinario.getNombre());
			pstmt.setString(2, nuevoVeterinario.getEspecie());
			pstmt.setString(3, nuevoVeterinario.getRaza());
			pstmt.setString(4, nuevoVeterinario.getSexo());
			pstmt.setInt(5, nuevoVeterinario.getEdad());
			pstmt.setFloat(6, nuevoVeterinario.getPeso());
			pstmt.setString(7, nuevoVeterinario.getObservaciones());
			pstmt.setString(8, nuevoVeterinario.getFecha());

			// Asignar imagen como InputStream si existe
			byte[] imageBytes = nuevoVeterinario.getImagen();
			InputStream imagenStream = (imageBytes != null) ? new ByteArrayInputStream(imageBytes) : null;
			pstmt.setBlob(9, imagenStream);

			pstmt.setInt(10, dameIDVeterinario(antiguoVeterinario));
			pstmt.executeUpdate();
			conexion.closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/** OBTENER EL ID DEL VETERINARIO SOLICITADO */
	public int dameIDVeterinario(Veterinario p) {
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
