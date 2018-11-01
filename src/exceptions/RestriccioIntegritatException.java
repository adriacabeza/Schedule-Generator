package exceptions;

public class RestriccioIntegritatException extends Exception {
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public RestriccioIntegritatException(String message) {
        super(message);
    }
}
