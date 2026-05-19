import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Preparar las dependencias
        ContactoDAO dao = new ContactoDAOImpl();
        ContactoServicio servicio = new ContactoServicio(dao);

        // 1. Insertar contactos
        System.out.println("=== INSERTAR ===");
        servicio.registrarContacto("Ana", "111111");
        servicio.registrarContacto("Luis", "222222");
        servicio.registrarContacto("Marta", "333333");

        // 2. Listar todos (AQUÍ SE USA EL CURSOR)
        System.out.println("\n=== LISTAR TODOS (CURSOR) ===");
        List<Contacto> lista = servicio.listarContactos();
        for (Contacto c : lista) {
            System.out.println(c);
        }

        // 3. Buscar por id
        System.out.println("\n=== BUSCAR POR ID ===");
        Contacto encontrado = servicio.buscarPorId(2);
        System.out.println(encontrado != null ? encontrado : "No encontrado");

        // 4. Actualizar
        System.out.println("\n=== ACTUALIZAR ===");
        servicio.actualizarContacto(2, "Luis Miguel", "666666");
        System.out.println("Después de actualizar: " + servicio.buscarPorId(2));

        // 5. Eliminar
        System.out.println("\n=== ELIMINAR ===");
        servicio.eliminarContacto(3);
        System.out.println("Lista tras eliminar id 3:");
        lista = servicio.listarContactos();
        for (Contacto c : lista) {
            System.out.println(c);
        }

        // 6. Cerrar conexión al final
        Singleton.close();
    }
}