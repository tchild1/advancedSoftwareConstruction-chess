package exceptions;

/**
 * This exception represents a 400 error
 */
public class BadRequestException extends Exception{

    /**
     * Throws a 400 exception
     * */
    public BadRequestException(String message) {
        super(message);
    }
}
