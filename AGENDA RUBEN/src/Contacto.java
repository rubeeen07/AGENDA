public class Contacto {
    private int id;
    private String nombre;
    private String telefono;

    // Constructor para contactos nuevos (sin id, lo asignará la BD)
    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Setter para el id (necesario cuando leemos de la BD)
    public void setId(int id) {
        this.id = id;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    // Para mostrar datos fácilmente en pruebas
    @Override
    public String toString() {
        return id + " - " + nombre + " - " + telefono;
    }
}
