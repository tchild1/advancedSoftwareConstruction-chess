package passoffTests.myTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import requests.ClearApplicationRequest;
import requests.LoginRequest;
import requests.RegisterUserRequest;
import responses.LoginResponse;
import responses.RegisterUserResponse;
import services.ClearApplicationService;
import services.LoginService;
import services.RegisterUserService;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    RegisterUserResponse testUser;

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