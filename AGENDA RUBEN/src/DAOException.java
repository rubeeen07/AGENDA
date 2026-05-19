
// Excepción personalizada no comprobada (unchecked)
public class DAOException extends RuntimeException {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}