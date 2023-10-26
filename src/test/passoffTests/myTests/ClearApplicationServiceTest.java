package passoffTests.myTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import requests.*;
import responses.CreateGameResponse;
import responses.ListGamesResponse;
import responses.RegisterUserResponse;
import services.*;

import static org.junit.jupiter.api.Assertions.*;

class ClearApplicationServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException, NotAuthorizedException {
        // Get a fresh start
        TestFactory.clearApplication();

        // create test user for Authorization
        TestFactory.createTestUser();
    }

    @Test
    void clearApplication() throws DataAccessException, NotAuthorizedException, ForbiddenException, BadRequestException {
        // create game and list to check it exists
        TestFactory.createGame(true);
        ListGamesResponse listGamesResponse = TestFactory.listGames(true);

        // there should be only one game
        assertEquals(listGamesResponse.getGames().size(), 1, "Before clearing the application, one game should exist");

        // clear the application
        TestFactory.clearApplication();

        // Test that the user does not exist
        LogoutRequest logoutRequest = new LogoutRequest(TestFactory.getTestUser().getAuthToken());
        assertThrowsExactly(NotAuthorizedException.class, () -> {
            new LogoutService().logout(logoutRequest);
        }, "User should not exist after clearing the application");

        // re-create test user for Authorization
        TestFactory.createTestUser();

        // check that there are no games left
        ListGamesResponse listGamesResponse2 = TestFactory.listGames(true);
        assertEquals(listGamesResponse2.getGames().size(), 0, "After clearing the application, No games should exist");
    }
}