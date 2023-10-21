package daos;

import models.AuthToken;
import models.User;

import java.util.ArrayList;

/**
 * This table provides access to user database tables
 */
public class UserDAO {

    /**
     * Creates a new user in the database
     *
     * @param newUser to be created
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void CreateUser(User newUser) throws DataAccessException {}

    /**
     * Deletes a user from the Database
     *
     * @param user to be deleted
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void DeleteUser(User user) throws DataAccessException {}

    /**
     * Deletes all users from the database
     *
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void DeleteAllUsers() throws DataAccessException {}

    /**
     * Gets a single user from the database by their authToken
     *
     * @param authToken of user to be retrieved
     * @return user of the given AuthToken
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public User GetUser(AuthToken authToken) throws DataAccessException {
        return null;
    }

    /**
     * Gets a list of all users from the Database
     *
     * @return a list of all users
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public ArrayList<User> GetAllUsers() throws DataAccessException {
        return null;
    }

    /**
     * Updates a User's information in the Database
     *
     * @param newUserInfo user object with the new information of the user
     * @param UserID of the user to be changed
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void UpdateUser(User newUserInfo, String UserID) throws DataAccessException {}
}
