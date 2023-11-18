package daos;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static daos.AuthDAO.*;
import static org.junit.jupiter.api.Assertions.*;
import static passoffTests.TestFactory.*;

class AuthDAOTest {

    @BeforeEach
    void setUp() throws ForbiddenException, SQLException, BadRequestException, DataAccessException, dataAccess.DataAccessException {
        clearApplication();
        createTestUser();
    }

    @Test
    void isAuthorizedPositive() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        assertTrue(IsAuthorized(getTestUser().getAuthToken()), "valid user was unauthorized");
    }

    @Test
    void isAuthorizedNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        assertFalse(IsAuthorized("Random Auth Token"), "Random auth token was accepted as valid");
    }

    @Test
    void addAuthTokenPositive() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        String testUser = "TestUser";
        AuthToken token = new AuthToken(testUser);
        AddAuthToken(token);

        assertTrue(GetAuthFromDB(testUser), "Could not find authtoken for a registered user");
    }

    @Test
    void addAuthTokenNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        String testUser = "TestUser";

        AuthToken token = new AuthToken(testUser);
        AddAuthToken(token);

        assertFalse(GetAuthFromDB("Random User"), "Random user was able to use the authtoken");
    }

    @Test
    void getUsernamePositive() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        String testUser = "TestUser";

        AuthToken token = new AuthToken(testUser);
        AddAuthToken(token);

        assertEquals(GetUsername(token.getAuthToken()), testUser, "authtoken got wrong username");
    }

    @Test
    void getUsernameNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        String testUser = "TestUser";

        AuthToken token = new AuthToken(testUser);
        AddAuthToken(token);

        assertNotEquals(GetUsername(token.getAuthToken()), "wrong user", "Wrong user was able to sign in");
    }

    @Test
    void deleteAuthTokenPositive() throws SQLException, DataAccessException, dataAccess.DataAccessException, NotAuthorizedException {
        String testUser = "TestUser";

        AuthToken token = new AuthToken(testUser);
        AddAuthToken(token);

        AuthDAO.DeleteAuthToken(token.getAuthToken());

        assertFalse(GetAuthFromDB(testUser), "authToken was not deleted");
    }

    @Test
    void deleteAuthTokenNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException, NotAuthorizedException {
        String testUser = "TestUser";

        AuthToken token = new AuthToken(testUser);
        AddAuthToken(token);

        AuthDAO.DeleteAuthToken(token.getAuthToken());

        assertFalse(GetAuthFromDB("Random User"), "authToken was deleted");
    }

    @Test
    void Clear() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        String testUser = "TestUser";

        AuthToken token = new AuthToken(testUser);
        AddAuthToken(token);

        AuthDAO.DeleteAllAuthTokens();

        assertFalse(GetAuthFromDB(testUser));
    }
}