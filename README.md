# Ejercicio S DEIN - Gestión de Veterinario

Este es un proyecto para gestionar animales de un veterinario en una base de datos, desarrollado utilizando JavaFX para la interfaz gráfica de usuario y conectando a una base de datos SQL para almacenar la información. El proyecto está diseñado para un sistema de gestión de veterinarios y sus animales, permitiendo realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar).
 
## Estructura del Proyecto

El proyecto está organizado en varias carpetas y archivos clave:

### 1. **`application/`**
Contiene las clases principales que manejan la lógica de la aplicación:
- **`S.java`**: Clase principal de la aplicación.
- **`SModal.java`**: Clase de ventana modal para operaciones adicionales con los animales o veterinarios.

### 2. **`bbdd/`**
Contiene la clase para gestionar la conexión a la base de datos:
- **`ConexionBD.java`**: Clase que establece la conexión con la base de datos y gestiona las operaciones de consulta y actualización.

### 3. **`controllers/`**
Contiene los controladores para las vistas JavaFX:
- **`SController.java`**: Controlador principal para la ventana principal de la aplicación.
- **`SModalController.java`**: Controlador para la ventana modal utilizada en la gestión de información de animales y veterinarios.

### 4. **`dao/`**
Contiene las clases de acceso a datos:
- **`VeterinarioDao.java`**: Clase que maneja las operaciones CRUD de los veterinarios en la base de datos.

### 5. **`model/`**
Contiene las clases que representan los modelos de datos utilizados en la aplicación:
- **`Animal.java`**: Clase que representa a un animal.
- **`Persona.java`**: Clase que representa a una persona.

### 6. **`util/`**
Contiene utilidades y configuraciones generales:
- **`Propiedades.java`**: Clase para gestionar las propiedades de configuración de la aplicación.

### 7. **`fxml/`**
Contiene los archivos de diseño de la interfaz gráfica:
- **`S.fxml`**: Fichero FXML para la ventana principal de la aplicación.
- **`S_modal.fxml`**: Fichero FXML para la ventana modal.

### 8. **`sql/`**
Contiene el script para la creación de la base de datos:
- **`veterinario.sql`**: Script SQL para crear las tablas necesarias en la base de datos.

### 9. **Archivos de configuración**
- **`configuration.properties`**: Archivo de configuración con las propiedades de la aplicación, como la conexión a la base de datos y otros parámetros de la aplicación.

### 10. **`module-info.java`**
Define los módulos de la aplicación para el sistema de módulos de Java.

## Requisitos

- **Java 11 o superior**
- **JavaFX**: Asegúrate de tener JavaFX configurado correctamente en tu entorno de desarrollo.
- **Base de Datos SQL**: El proyecto utiliza una base de datos SQL. El script `veterinario.sql` debe ejecutarse para crear las tablas necesarias.

## Configuración de la Base de Datos

1. Ejecuta el archivo `veterinario.sql` en tu base de datos SQL para crear las tablas.
2. Configura los parámetros de conexión a la base de datos en el archivo `configuration.properties`.

## Ejecución del Proyecto

1. Asegúrate de que JavaFX esté correctamente configurado en tu IDE.
2. Abre la clase `S.java` y ejecuta la aplicación.
3. La interfaz de usuario te permitirá gestionar veterinarios y animales.

## Funcionalidades

- **Gestión de Animales**: Permite agregar, modificar y eliminar animales, así como asociarlos con veterinarios.
- **Ventanas Modales**: Se usan para gestionar detalles adicionales de los animales y veterinarios.

## Personalización

Puedes modificar los parámetros de configuración de la base de datos en el archivo `configuration.properties` y personalizar la interfaz gráfica modificando los archivos FXML.
