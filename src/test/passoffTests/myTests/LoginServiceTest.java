package passoffTests.myTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import responses.LoginResponse;
import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException {
        // Get a fresh start
        TestFactory.clearApplication();

        // create test user for Authorization
        TestFactory.createTestUser();
    }

    @Test
    void loginPositive() throws NotAuthorizedException, DataAccessException {

        LoginResponse response =TestFactory.login(true);

        assertEquals(response.getUsername(), TestFactory.getTestUsername(), "Username of logged in user is incorrect");
        assertNotNull(response.getToken(), "AuthToken should not be null");
    }

    @Test
    void loginNegative() throws NotAuthorizedException, DataAccessException {

        // login with incorrect credentials
        assertThrowsExactly(NotAuthorizedException.class, ()-> {
            TestFactory.login(false);
        }, "Wrong password should throw an error");
    }
}