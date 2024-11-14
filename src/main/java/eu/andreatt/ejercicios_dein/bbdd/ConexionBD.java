package eu.andreatt.ejercicios_dein.bbdd;

import eu.andreatt.ejercicios_dein.util.Propiedades;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

/**
 * Clase que gestiona la conexión con la base de datos.
 * Utiliza las propiedades de conexión definidas en la clase {@link Propiedades}.
 */
public class ConexionBD {

    /** Conexión activa con la base de datos. */
    private Connection conexion;

    /**
     * Constructor que establece una conexión con la base de datos.
     * Obtiene los parámetros de conexión (URL, usuario y contraseña) de la clase {@link Propiedades}.
     * La zona horaria del servidor se configura según la zona horaria del sistema.
     */
    public ConexionBD() {
        try {
            String url = Propiedades.getValor("url") + "?serverTimezone=" + TimeZone.getDefault().getID();
            String user = Propiedades.getValor("user");
            String password = Propiedades.getValor("password");

            conexion = DriverManager.getConnection(url, user, password);
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            mostrarAlerta("Error: No se pudo establecer la conexión con la base de datos.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Obtiene la conexión actual con la base de datos.
     *
     * @return La conexión activa de tipo {@link Connection}, o null si la conexión falló.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Verifica si la conexión con la base de datos es válida.
     *
     * @return true si la conexión está activa, false si no.
     */
    public boolean verificarConexion() {
        try {
            if (conexion != null && !conexion.isClosed() && conexion.isValid(2)) {
                return true;
            }
        } catch (SQLException e) {

        }
        return false;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public void closeConnection() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                mostrarAlerta("Error: No se pudo cerrar la conexión con la base de datos.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    /**
     * Muestra una alerta con un mensaje específico.
     *
     * @param mensaje El mensaje a mostrar en la alerta.
     * @param tipo El tipo de alerta (advertencia, error, etc.).
     */
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);  // No se muestra un encabezado en la alerta
        alert.setContentText(mensaje);  // Establecer el mensaje en el contenido
        alert.showAndWait();  // Mostrar la alerta y esperar a que el usuario la cierre
    }
}
