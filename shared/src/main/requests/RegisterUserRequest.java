package requests;

/**
 * class representing a user's request to create a new user
 */
public class RegisterUserRequest {

    /**
     * username the user is requesting
     */
    String username;

    /**
     * password the user is setting
     */
    String password;

    /**
     * the new user's email
     */
    String email;

    /**
     * Constructor creating a RegisterUserRequest object with new
     * user's information
     *
     * @param username username requested by user
     * @param password password set by user
     * @param email user's email
     */
    public RegisterUserRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
