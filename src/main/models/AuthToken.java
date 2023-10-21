package models;

/**
 * Provides structure of an AuthToken associated with each user
 */
public class AuthToken {

    /**
     * UUID given to each user when they sign in
     */
    String authToken;

    /**
     * name of the user as stored in the database
     */
    String userName;

    /**
     * Creates a new AuthToken
     *
     * @param authToken string with AuthToken for a user
     * @param userName of user with this AuthToken
     */
    public AuthToken(String authToken, String userName) {
        this.authToken = authToken;
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }
}
