package resposnses;

/**
 * base class for all response classes
 */
public class Response {

    /**
     * boolean value stating the success/failure of the request
     */
    boolean success;

    /**
     * message stating why a request failed (if applicable)
     */
    String message;

    /**
     * Base class constructor for Response Objects
     *
     * @param message stating reason for failure (if applicable)
     * @param success boolean value stating success/failure of request
     */
    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
