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
import requests.LogoutRequest;
import requests.RegisterUserRequest;
import responses.RegisterUserResponse;
import services.ClearApplicationService;
import services.LogoutService;
import services.RegisterUserService;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {

    RegisterUserResponse testUser;

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException {
        // Get a fresh start
        TestFactory.clearApplication();

        // create test user for Authorization
        TestFactory.createTestUser();
    }

    @Test
    void logoutPositive() throws NotAuthorizedException, DataAccessException {

        assertDoesNotThrow(() -> {
            TestFactory.logout(true);
        }, "Logout request threw an error");
    }

    @Test
    void logoutNegative() throws NotAuthorizedException, DataAccessException {

        assertThrowsExactly(NotAuthorizedException.class, () -> {
            TestFactory.logout(false);
        }, "Logout request should have failed with a fake AuthToken");
    }
}