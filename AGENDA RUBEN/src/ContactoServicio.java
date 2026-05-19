import java.util.List;

public class ContactoServicio {
    private ContactoDAO dao;

    // El DAO se inyecta por constructor (no sabe qué implementación concreta tiene)
    public ContactoServicio(ContactoDAO dao) {
        this.dao = dao;
    }

    // Registrar un nuevo contacto
    public void registrarContacto(String nombre, String tel) {
        if (nombre.isEmpty() || tel.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        dao.guardar(new Contacto(nombre, tel));
    }

    // Actualizar contacto existente
    public void actualizarContacto(int id, String nombre, String tel) {
        if (nombre.isEmpty() || tel.isEmpty()) {
            throw new IllegalArgumentException("Nombre y teléfono no pueden estar vacíos");
        }
        Contacto c = new Contacto(nombre, tel);
        c.setId(id);
        dao.actualizar(c);
    }

    // Eliminar por id
    public void eliminarContacto(int id) {
        dao.eliminar(id);
    }

    // Buscar un contacto por id
    public Contacto buscarPorId(int id) {
        return dao.obtenerPorId(id);
    }

    // Listar todos los contactos
    public List<Contacto> listarContactos() {
        return dao.obtenerTodos();
    }
}