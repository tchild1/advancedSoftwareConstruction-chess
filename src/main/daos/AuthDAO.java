package daos;

import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import models.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides access to authorization database tables
 */
public class AuthDAO {

    /**
     * Checks if a user is authorized by checking their authToken is in the database
     *
     * @param token of the user we want to check
     * @return Boolean authorized or not
     * @throws DataAccessException if error extracting data
     */
    public static Boolean IsAuthorized(String token) throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "SELECT token FROM chess.auth WHERE token=?;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, token);
        ResultSet result = statement.executeQuery();

        boolean response = result.next();
        new Database().closeConnection(connection);

        return response;
    }

    /**
     * Adds an AuthToken to the Database
     *
     * @param authToken to be added to the database
     * @throws DataAccessException if error extracting data
     */
    public static void AddAuthToken(AuthToken authToken) throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "INSERT INTO chess.auth (username, token) VALUES (?, ?)";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, authToken.getUserName());
        statement.setString(2, authToken.getAuthToken());

        statement.executeUpdate();
        new Database().closeConnection(connection);
    }

    /**
     * Get username with an Authtoken
     *
     * @param authToken of the user
     * @return username of the user with given authToken
     * @throws DataAccessException if error extracting data
     */
    public static String GetUsername(String authToken) throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "SELECT username FROM chess.auth WHERE token=?;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, authToken);
        ResultSet result = statement.executeQuery();

        String username;
        if (result.next()) {
            username = result.getString(1);
        } else {
            username = null;
        }
        new Database().closeConnection(connection);
        return username;
    }

    /**
     * Removes the user from Auth database when signing out
     *
     * @param authToken of the user to be removed
     * @throws DataAccessException if error extracting data
     */
    public static void DeleteAuthToken(String authToken) throws DataAccessException, NotAuthorizedException, dataAccess.DataAccessException, SQLException {
        String sql = "DELETE FROM chess.auth WHERE token=?;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, authToken);

        statement.executeUpdate();
        new Database().closeConnection(connection);
    }

    /**
     * Deletes all authTokens from the database
     * @throws DataAccessException thrown if error occurs when accessing data
     */
    public static void DeleteAllAuthTokens() throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "DELETE FROM chess.auth;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        new Database().closeConnection(connection);
    }
}
