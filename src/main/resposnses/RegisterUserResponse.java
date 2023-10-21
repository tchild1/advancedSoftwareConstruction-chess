package resposnses;

/**
 * response to a request to register a new user
 */
public class RegisterUserResponse extends Response {

    /**
     * username of the new user
     */
    String username;

    /**
     * authToken of the new user
     */
    String authToken;

    /**
     * Constructor for the RegisterUserResponse class
     *
     * @param message -error message accompanying status code (if error)
     * @param username of the new user (if success)
     * @param authToken of the new user (if success)
     */
    public RegisterUserResponse(String username, String authToken, String message, boolean success) {
        super(message, success);
        this.username = username;
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }
}
