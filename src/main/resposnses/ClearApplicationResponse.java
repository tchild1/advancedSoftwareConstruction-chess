package resposnses;

/**
 * class stating a response to a request to clear all application data
 */
public class ClearApplicationResponse extends Response {

    /**
     * Constructor for the ClearApplicationResponses
     *
     * @param success boolean value stating if request was successful
     * @param message accompanying a failed request
     */
    public ClearApplicationResponse(Boolean success, String message) {
        super(message, success);
    }

}
