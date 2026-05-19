
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ContactoDAOImpl implements ContactoDAO {

    // ---------- INSERTAR ----------
    @Override
    public void guardar(Contacto c) {
        String sql = "INSERT INTO contactos (nombre, telefono) VALUES (?, ?)";
        // try-with-resources: cierra automáticamente Connection y PreparedStatement
        try (Connection conn = Singleton.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());   // Primer ? → nombre
            ps.setString(2, c.getTelefono()); // Segundo ? → teléfono
            ps.executeUpdate();               // Ejecuta el INSERT

        } catch (SQLException e) {
            throw new DAOException("Error al insertar contacto", e);
        }
    }

    // ---------- ACTUALIZAR ----------
    @Override
    public void actualizar(Contacto c) {
        String sql = "UPDATE contactos SET nombre = ?, telefono = ? WHERE id = ?";
        try (Connection conn = Singleton.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setInt(3, c.getId());          // Tercer ? → id
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("No se encontró el contacto con id " + c.getId(), null);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar contacto", e);
        }
    }

    // ---------- ELIMINAR ----------
    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM contactos WHERE id = ?";
        try (Connection conn = Singleton.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("No se encontró el contacto con id " + id, null);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar contacto", e);
        }
    }

    // ---------- BUSCAR POR ID ----------
    @Override
    public Contacto obtenerPorId(int id) {
        String sql = "SELECT id, nombre, telefono FROM contactos WHERE id = ?";
        try (Connection conn = Singleton.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {  // executeQuery para SELECT
                if (rs.next()) {  // Si hay una fila, avanza el cursor a ella
                    Contacto c = new Contacto(rs.getString("nombre"), rs.getString("telefono"));
                    c.setId(rs.getInt("id"));
                    return c;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener contacto por id", e);
        }
        return null; // No encontrado
    }

    // ---------- LISTAR TODOS (con cursor) ----------
    @Override
    public List<Contacto> obtenerTodos() {
        String sql = "SELECT id, nombre, telefono FROM contactos";
        List<Contacto> lista = new ArrayList<>();

        // Statement (sin parámetros) y ResultSet para recorrer filas
        try (Connection conn = Singleton.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // El cursor (ResultSet) empieza ANTES de la primera fila.
            // rs.next() mueve a la siguiente fila. Devuelve true si hay fila.
            while (rs.next()) {
                Contacto c = new Contacto(
                        rs.getString("nombre"),
                        rs.getString("telefono")
                );
                c.setId(rs.getInt("id"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener todos los contactos", e);
        }
        return lista;
    }
}