package requests;

import models.AuthToken;

/**
 * class representing a request to list all games currently being played
 */
public class ListGamesRequest extends Request {

    /**
     * Token authorizing a user to list all games currently being played
     */
    AuthToken authToken;

    /**
     * creates a request to list all current games
     * @param authToken of user making the request
     */
    public ListGamesRequest(AuthToken authToken) {
        super(RequestMethods.GET);
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
