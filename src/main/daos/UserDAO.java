package daos;

import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
    public void CreateUser(User newUser) throws DataAccessException, ForbiddenException, dataAccess.DataAccessException, SQLException {
        String sqlCheck = "SELECT username FROM chess.user WHERE username=?;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sqlCheck);
        statement.setString(1, newUser.getUsername());
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            throw new ForbiddenException("Error: already taken");
        } else {
            String sqlUpdate = "INSERT INTO chess.user (username, password, email)" +
                               "VALUES (?, ?, ?)";

            PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdate);
            statementUpdate.setString(1, newUser.getUsername());
            statementUpdate.setString(2, newUser.getPassword());
            statementUpdate.setString(3, newUser.getEmail());

            statementUpdate.executeUpdate();
        }
        new Database().closeConnection(connection);
    }

    /**
     * Deletes all users from the database
     *
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void DeleteAllUsers() throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "DELETE FROM chess.user;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        new Database().closeConnection(connection);
    }

    /**
     * Determines whether a User is Authenticated or not
     *
     * @param Username of user
     * @param Password of password
     * @return boolean
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public Boolean AuthenticateUser(String Username, String Password) throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "SELECT username, password FROM chess.user WHERE username=?;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Username);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            return Objects.equals(Password, result.getString(2));
        } else {
            return false;
        }
    }
}
