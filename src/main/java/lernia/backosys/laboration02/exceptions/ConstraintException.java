package lernia.backosys.laboration02.exceptions;

public class ConstraintException extends RuntimeException {
    private final String id;

    public ConstraintException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
