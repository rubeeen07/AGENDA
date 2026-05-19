
import java.util.List;

public interface ContactoDAO {
    void guardar(Contacto c);
    void actualizar(Contacto c);
    void eliminar(int id);
    Contacto obtenerPorId(int id);
    List<Contacto> obtenerTodos();   // Devuelve todos los contactos (usa cursor)
}
