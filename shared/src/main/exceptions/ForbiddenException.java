package exceptions;

/**
 * This Exception represents a 403 error
 */
public class ForbiddenException extends Exception {

    /**
     * Throws a 403 exception
     * */
    public ForbiddenException(String message) {
        super(message);
    }
}
