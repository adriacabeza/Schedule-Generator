package exceptions;

public class NotFoundException extends Exception {

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
