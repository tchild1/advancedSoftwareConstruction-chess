package daos;

import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides access to authorization database tables
 */
public class AuthDAO {

    /**
     * This static variable maps each user's authToken to their username
     */
    public static Map<String, String> dictionary = new HashMap<>();

    /**
     * Checks if a user is authorized by checking their authToken is in the database
     *
     * @param token of the user we want to check
     * @return Boolean authorized or not
     * @throws DataAccessException if error extracting data
     */
    public static Boolean IsAuthorized(String token) throws DataAccessException {
        return dictionary.containsKey(token);
    }

    /**
     * Adds an AuthToken to the Database
     *
     * @param authToken to be added to the database
     * @throws DataAccessException if error extracting data
     */
    public static void AddAuthToken(AuthToken authToken) throws DataAccessException {
        dictionary.put(authToken.getAuthToken(), authToken.getUserName());
    }

    /**
     * Get username with an Authtoken
     *
     * @param authToken of the user
     * @return username of the user with given authToken
     * @throws DataAccessException if error extracting data
     */
    public static String GetUsername(String authToken) throws DataAccessException {
        return dictionary.get(authToken);
    }

    /**
     * Removes the user from Auth database when signing out
     *
     * @param authToken of the user to be removed
     * @throws DataAccessException if error extracting data
     */
    public void DeleteAuthToken(String authToken) throws DataAccessException, NotAuthorizedException {
        dictionary.remove(authToken);
    }

    /**
     * Deletes all authTokens from the database
     * @throws DataAccessException thrown if error occurs when accessing data
     */
    public void DeleteAllAuthTokens() throws DataAccessException {
        dictionary.clear();
    }
}
