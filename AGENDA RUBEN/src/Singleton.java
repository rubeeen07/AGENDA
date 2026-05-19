import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Singleton {

    // Única instancia de Connection (estática para compartirla)
    private static Connection conn = null;

    // Constructor privado → solo se puede llamar desde dentro de la clase
    private Singleton() {
        String url = "jdbc:mariadb://localhost:3306/programacion";
        String usuario = "programacion";
        String password = "programacion";

        try {
            // Establece la conexión con la base de datos
            conn = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método público que devuelve la única conexión (la crea si hace falta)
    public static Connection getInstance() {
        try {
            // Si la conexión no existe o está cerrada, la crea y asegura la tabla
            if (conn == null || conn.isClosed()) {
                new Singleton();               // Crea la conexión
                crearTablaSiNoExiste();        // Crea la tabla si no existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Crea la tabla 'contactos' si no existe (DDL)
    private static void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS contactos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(50) NOT NULL," +
                "telefono VARCHAR(50) NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al inicializar la tabla de contactos", e);
        }
    }

    // Cierra la conexión (se llama al final del programa)
    public static void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión");
            }
        }
    }
}
