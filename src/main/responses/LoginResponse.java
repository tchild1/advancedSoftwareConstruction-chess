package responses;

/**
 * response to a request to login
 */
public class LoginResponse extends Response {

    /**
     * token given to the user signing in
     */
    String authToken;

    /**
     * username of the user signing in
     */
    String username;

    /**
     * Response object with an AuthToken for user signing in
     *
     * @param authToken AuthToken to return to the user
     * @param username of the user signing in
     */
    public LoginResponse(String authToken, String username) {
        super(null);
        this.authToken = authToken;
        this.username = username;
    }

    public String getToken() {
        return authToken;
    }

    public void setToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}