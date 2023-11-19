package requests;

/**
 * class representing a request to login
 */
public class LoginRequest extends Request {

    /**
     * username of the user trying to sign in
     */
    String username;

    /**
     * password of the user trying to sign in
     */
    String password;

    /**
     * Constructor for Login Requests
     *
     * @param username of the user attempting to log in
     * @param password of the user attempting to log in
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
