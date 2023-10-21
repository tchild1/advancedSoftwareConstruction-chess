package resposnses;

/**
 * response to a request to join a game
 */
public class JoinGameResponse extends Response {

    /**
     * Constructor creating a join game response
     *
     * @param success boolean value stating success/failure of request
     * @param errorMessage message stating reason for failure (if applicable)
     */
    public JoinGameResponse(boolean success, String errorMessage) {
        super(errorMessage, success);
    }
}
