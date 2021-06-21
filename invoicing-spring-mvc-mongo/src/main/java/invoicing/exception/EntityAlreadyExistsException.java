package invoicing.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
