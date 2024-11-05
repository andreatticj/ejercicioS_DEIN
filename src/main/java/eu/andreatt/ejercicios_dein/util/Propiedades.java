package eu.andreatt.ejercicios_dein.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para cargar y acceder a propiedades desde un archivo
 * de configuración en el classpath.
 *
 * Esta clase se inicializa automáticamente al ser utilizada y carga
 * las propiedades de un archivo llamado 'configuration.properties'.
 */
public class Propiedades {
    /** Objeto de propiedades que almacena las claves y sus valores. */
    private static final Properties props = new Properties();

    static {
        // Carga el archivo de propiedades desde el classpath
        try (InputStream input = Propiedades.class.getResourceAsStream("/eu/andreatt/ejercicios_dein/configuration.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo configuration.properties en el classpath.");
            }
            props.load(input); // Carga las propiedades desde el archivo
        } catch (Exception e) {
            e.printStackTrace();  // Imprime la pila de la excepción para depuración
        }
    }

    /**
     * Obtiene el valor asociado a una clave desde el archivo de propiedades
     * situado en el classpath.
     *
     * @param clave La clave cuyo valor se desea obtener.
     * @return El valor asociado a la clave.
     * @throws RuntimeException Si la clave no tiene un valor asociado en el archivo de propiedades.
     */
    public static String getValor(String clave) {
        String valor = props.getProperty(clave); // Obtiene el valor de la clave proporcionada
        if (valor != null) {
            return valor; // Devuelve el valor si se encuentra
        } else {
            // Muestra las claves disponibles en caso de que la clave no se encuentre
            System.out.println("Claves disponibles en el archivo de propiedades: " + props.keySet());
            throw new RuntimeException("Clave '" + clave + "' no encontrada en el archivo de propiedades.");
        }
    }
}