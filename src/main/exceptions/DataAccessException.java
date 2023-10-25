package exceptions;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception{

    /**
     * Throws an exception when there is an error accessing the database
     *
     * @param message accompanying the exception
     */
    public DataAccessException(String message) {
        super(message);
    }
}