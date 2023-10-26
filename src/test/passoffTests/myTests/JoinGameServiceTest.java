package passoffTests.myTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import requests.JoinGameRequest;
import static org.junit.jupiter.api.Assertions.*;

class JoinGameServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException, NotAuthorizedException {
        // Get a fresh start
        TestFactory.clearApplication();

        // create test user for Authorization
        TestFactory.createTestUser();

        // Create a game to join
        TestFactory.createGame(true);
    }

    @Test
    void joinGamePositive() throws ForbiddenException, BadRequestException, NotAuthorizedException, DataAccessException {

        // joining a game should not throw an exception
        assertDoesNotThrow(() -> {
            TestFactory.joinGame(JoinGameRequest.PlayerColor.WHITE);
        }, "Joining a game threw an error");
    }

    @Test
    void joinGameNegative() throws ForbiddenException, BadRequestException, NotAuthorizedException, DataAccessException {
        TestFactory.joinGame(JoinGameRequest.PlayerColor.WHITE);

        // try adding a second user to the same color
        assertThrowsExactly(ForbiddenException.class, () -> {
            TestFactory.joinGame(JoinGameRequest.PlayerColor.WHITE);
        }, "Should have thrown an error when trying to add two users to play white");
    }
}