package models;

/**
 * Class representing a user
 */
public class User {

    /**
     * Username of the user as in the database
     */
    String username;

    /**
     * User's password
     */
    String password;

    /**
     * User's email
     */
    String email;

    /**
     * Constructor to create a new user
     *
     * @param username of user
     * @param password of user
     * @param email of user
     */
    public User(String username, String password, String email) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
