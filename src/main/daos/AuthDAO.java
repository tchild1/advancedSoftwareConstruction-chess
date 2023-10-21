package daos;

import models.AuthToken;

/**
 * This class provides access to authorization database tables
 */
public class AuthDAO {

    /**
     * connects to the database and retrieves the user associated with an AuthToken
     *
     * @param token of the user we want to retrieve
     * @return ID of the User's AuthToken
     */
    public String GetUserID(AuthToken token) {
        return null;
    }

    /**
     * Adds an AuthToken to the Database
     *
     * @param authToken to be added to the database
     */
    public void AddAuthToken(AuthToken authToken) {}

    /**
     * Deletes the AuthToken of a given user from the Database
     *
     * @param userID of the AuthToken to be removed
     */
    public void DeleteAuthToken(String userID) {}
}
