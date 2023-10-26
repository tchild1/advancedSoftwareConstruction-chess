package passoffTests.myTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import requests.ClearApplicationRequest;;
import responses.CreateGameResponse;
import services.ClearApplicationService;
import services.CreateGameService;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException {
        // get a fresh start
        new ClearApplicationService().clearApplication(new ClearApplicationRequest(new AuthToken("UNIT_TESTS")));

        // create test user for Authorization
        TestFactory.createTestUser();
    }

    @Test
    void createGamePositive() throws NotAuthorizedException, DataAccessException {
        CreateGameResponse response = TestFactory.createGame(true);
        assertEquals(String.valueOf(CreateGameService.getCurrID()), response.getGameID(), "Game returned incorrect ID");

        CreateGameResponse response2 = TestFactory.createGame(true);
        assertEquals(String.valueOf(CreateGameService.getCurrID()), response2.getGameID(), "Game returned incorrect ID");
    }

    @Test
    void createGameNegative() throws NotAuthorizedException, DataAccessException {

        // try creating a game with invalid credentials
        assertThrowsExactly(NotAuthorizedException.class, () -> {
            CreateGameResponse response = TestFactory.createGame(false);
        }, "Create Game accepted invalid authtoken");
    }
}
