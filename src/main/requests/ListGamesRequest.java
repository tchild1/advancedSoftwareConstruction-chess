package requests;

/**
 * class representing a request to list all games currently being played
 */
public class ListGamesRequest extends Request {

    /**
     * Token authorizing a user to list all games currently being played
     */
    String authToken;

    /**
     * creates a request to list all current games
     * @param authToken of user making the request
     */
    public ListGamesRequest(String authToken) {
        super(RequestMethods.GET);
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
