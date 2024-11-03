package eu.andreatt.ejercicios_dein.dao;

import eu.andreatt.ejercicios_dein.bbdd.ConexionBD;
import eu.andreatt.ejercicios_dein.model.Veterinario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeterinarioDao {
	private ConexionBD conexion;

	/** CARGAR LOS ANIMALES EXISTENTES EN BASE DE DATOS */
	public ObservableList<Veterinario> cargarVeterinario() {

		ObservableList<Veterinario> veterinario = FXCollections.observableArrayList();
		try {
			conexion = new ConexionBD();
			String consulta = "select * from Veterinario";
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


				veterinario.add(new Veterinario(nombreAnimal, especieAnimal, razaAnimal, sexoAnimal, edadAnimal, pesoAnimal ,observacionesAnimal, fechaPrimeraConsulta));
			}
			rs.close();
			conexion.closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return veterinario;
	}

	/** INSERTAR ANIMAL EN BASE DE DATOS */
	public boolean nuevoVeterinario(Veterinario p) {

		ObservableList<Veterinario> veterinario = cargarVeterinario();

		if (veterinario.contains(p)) {
			return false;
		} else {
			try {
				conexion = new ConexionBD();

				int id = dameMaxID() + 1;

				String consulta = "insert into Veterinario values(" + id + ",'" + p.getNombre() + "','" + p.getEspecie() + "','" + p.getRaza() + "','" + p.getSexo() + "'," + p.getEdad() + "," + p.getPeso() + ",'" + p.getObservaciones() + "','" + p.getFecha() + "'," + null +")";
				System.out.println(consulta);
				PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
				pstmt.executeUpdate();
				conexion.closeConnection();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/** BORRAR ANIMAL EN BASE DE DATOS */
	public void borrarVeterinario(Veterinario p) {
		try {
			conexion = new ConexionBD();
			//Considero mismo animal nombre y raza
			String consulta = "delete from Veterinario where id = (select id from Persona where nombre='" + p.getNombre() + "' and raza='" + p.getRaza() + "')";
			System.out.println(consulta);
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			pstmt.executeUpdate();
			conexion.closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** ACTUALIZAR ANIMAL EN BASE DE DATOS */
	public void modificarVeterinario(Veterinario antiguoVeterinario, Veterinario nuevoVeterinario)  {
		try {
			conexion = new ConexionBD();       
        	String consulta = "UPDATE Veterinario SET nombre = ?, especie = ?, raza = ?, sexo = ?, edad = ?, peso = ?, observaciones = ?, fecha_primera_consulta = ? WHERE id = ?";
        	System.out.println(consulta);

	    	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta); 
	    	pstmt.setString(1, nuevoVeterinario.getNombre());
	    	pstmt.setString(2, nuevoVeterinario.getEspecie());
	    	pstmt.setString(3, nuevoVeterinario.getRaza());
	    	pstmt.setString(4, nuevoVeterinario.getSexo());
	    	pstmt.setInt(5, nuevoVeterinario.getEdad());
	    	pstmt.setFloat(6, nuevoVeterinario.getPeso());
	    	pstmt.setString(7, nuevoVeterinario.getObservaciones());
	    	pstmt.setString(8, nuevoVeterinario.getFecha());
	    	pstmt.setInt(9, dameIDVeterinario(antiguoVeterinario));
        	     
        	pstmt.executeUpdate();        
        	conexion.closeConnection();
        
	    } catch (SQLException e) {	    	
	    	e.printStackTrace();
	    }    
	}

	/** OBTENER EL ID DEL ANIMAL SOLICITADO */
	public int dameIDVeterinario(Veterinario p) {

		try {
			conexion = new ConexionBD();
			String consulta = "select id from Veterinario where nombre='" + p.getNombre() + "' and raza='" + p.getRaza() + "' and edad = " + p.getEdad(); 
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				return rs.getInt("ID");
			}
			rs.close();
			conexion.closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/** OBTENER EL ID MÁS GRANDE DE LOS ANIMALES EN BASE DE DATOS */
	public int dameMaxID() {

		try {
			conexion = new ConexionBD();
			String consulta = "select max(id) as ID from Veterinario";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				return rs.getInt("ID");
			}
			rs.close();
			conexion.closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
}