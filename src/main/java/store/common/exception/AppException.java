package store.common.exception;

public class AppException extends IllegalArgumentException {
    public AppException(String message, Exception e) {
        super(message, e);
    }
}
