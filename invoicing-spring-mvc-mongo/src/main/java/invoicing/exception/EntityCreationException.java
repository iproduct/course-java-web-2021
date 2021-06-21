package invoicing.exception;

public class EntityCreationException extends RuntimeException {
    public EntityCreationException() {
    }

    public EntityCreationException(String message) {
        super(message);
    }

    public EntityCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityCreationException(Throwable cause) {
        super(cause);
    }
}
