package responses;

/**
 * base class for all response classes
 */
public class Response {

    /**
     * message stating why a request failed (if applicable)
     */
    private String message;

    /**
     * Base class constructor for Response Objects
     *
     * @param message stating reason for failure (if applicable)
     */
    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
