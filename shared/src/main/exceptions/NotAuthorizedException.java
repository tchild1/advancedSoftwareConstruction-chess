package exceptions;

/**
 * This exception represents a 401 Error
 */
public class NotAuthorizedException extends Exception {

    /**
     * Throws an exception when a user signing in is not authorized
     */
    public NotAuthorizedException(String message) {
        super(message);
    }
}
