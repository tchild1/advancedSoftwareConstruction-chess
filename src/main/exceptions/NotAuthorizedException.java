package exceptions;

public class NotAuthorizedException extends Exception {
    /**
     * Throws an exception when a user signing in is not authorized
     */
    public NotAuthorizedException(String message) {
        super(message);
    }
}
