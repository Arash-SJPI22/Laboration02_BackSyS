package lernia.backosys.laboration02.exceptions;

public class NotAuthorizedException extends RuntimeException {
    private final String id;

    public NotAuthorizedException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
