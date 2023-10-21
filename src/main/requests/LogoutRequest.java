package requests;

import models.AuthToken;

/**
 * class representing a request to logout
 */
public class LogoutRequest extends Request {

    /**
     * token authorizing a user to sign out
     */
    AuthToken authToken;

    /**
     * creates a request to logout of a game
     *
     * @param authToken of user trying to logout
     */
    public LogoutRequest(AuthToken authToken) {
        super(RequestMethods.DELETE);
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
