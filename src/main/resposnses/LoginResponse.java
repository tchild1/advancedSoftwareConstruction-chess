package resposnses;

import models.AuthToken;

/**
 * response to a request to login
 */
public class LoginResponse extends Response {

    /**
     * token given to the user signing in
     */
    AuthToken token;

    /**
     * username of the user signing in
     */
    String username;

    /**
     * Response object with an AuthToken for user signing in
     *
     * @param token AuthToken to return to the user
     * @param username of the user signing in
     * @param message if an error, message to return to user
     * @param success boolean value stating success/failure of the request
     */
    public LoginResponse(AuthToken token, String username, String message, boolean success) {
        super(message, success);
        this.token = token;
        this.username = username;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}