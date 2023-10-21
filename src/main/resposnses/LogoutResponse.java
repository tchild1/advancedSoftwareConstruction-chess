package resposnses;

/**
 * response to a request to logout
 */
public class LogoutResponse extends Response {

    /**
     * Constructor of logout response
     *
     * @param errorMessage stating reason for failure (if applicable)
     * @param success boolean value stating success/failure of request
     */
    public LogoutResponse(String errorMessage, boolean success) {
        super(errorMessage, success);
    }
}
