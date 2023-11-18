package daos;

import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static daos.UserDAO.CreateUser;
import static org.junit.jupiter.api.Assertions.*;
import static passoffTests.TestFactory.GetUserInfoFromDB;
import static passoffTests.TestFactory.clearApplication;

class UserDAOTest {

    @BeforeEach
    void setUp() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        clearApplication();
    }

    @Test
    void createUserPositive() throws ForbiddenException, SQLException, DataAccessException, dataAccess.DataAccessException {
        User user = new User("TestUser", "pass", "childtanner@gmail.com");
        CreateUser(user);

        ResultSet results = GetUserInfoFromDB("TestUser");
        assertTrue(results.next(), "User should have been created");
    }

    @Test
    void createUserNegative() throws ForbiddenException, SQLException, DataAccessException, dataAccess.DataAccessException {
        User user = new User("TestUser", "pass", "childtanner@gmail.com");
        User user2 = new User("TestUser", "pass", "childtanner@gmail.com");

        CreateUser(user);
        assertThrows(ForbiddenException.class, () -> {
            CreateUser(user2);
        });
    }

    @Test
    void Clear() throws ForbiddenException, SQLException, DataAccessException, dataAccess.DataAccessException {
        User user = new User("TestUser", "pass", "childtanner@gmail.com");
        CreateUser(user);

        UserDAO.DeleteAllUsers();

        ResultSet results = GetUserInfoFromDB("TestUser");
        assertFalse(results.next());
    }

    @Test
    void authenticateUserPositive() throws ForbiddenException, SQLException, DataAccessException, dataAccess.DataAccessException {
        String Username = "user";
        String Password = "pass";
        String Email = "childtanner";

        CreateUser(new User(Username, Password, Email));

        assertTrue(UserDAO.AuthenticateUser(Username, Password));
    }

    @Test
    void authenticateUserNegative() throws ForbiddenException, SQLException, DataAccessException, dataAccess.DataAccessException {
        String Username = "user";
        String Password = "pass";
        String Email = "childtanner";

        CreateUser(new User(Username, Password, Email));

        assertFalse(UserDAO.AuthenticateUser(Username, "Password"));
    }
}