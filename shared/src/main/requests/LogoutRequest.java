package requests;

/**
 * class representing a request to logout
 */
public class LogoutRequest {

    /**
     * token authorizing a user to sign out
     */
    String authToken;

    /**
     * creates a request to logout of a game
     *
     * @param authToken of user trying to log out
     */
    public LogoutRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
