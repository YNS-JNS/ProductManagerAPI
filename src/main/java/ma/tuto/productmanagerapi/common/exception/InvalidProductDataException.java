package ma.tuto.productmanagerapi.common.exception;

public class InvalidProductDataException extends RuntimeException {
    public InvalidProductDataException(String message) {
        super(message);
    }
}
