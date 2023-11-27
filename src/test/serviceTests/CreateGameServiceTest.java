package serviceTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import requests.ClearApplicationRequest;;
import responses.CreateGameResponse;
import responses.Response;
import services.ClearApplicationService;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException, SQLException, dataAccess.DataAccessException {
        // get a fresh start
        new ClearApplicationService().clearApplication(new ClearApplicationRequest());

        // create test user for Authorization
        TestFactory.createTestUser();
    }

    @Test
    void createGamePositive() throws NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {
        Response response = TestFactory.createGame(true);
        assertEquals(null, response.getMessage(), "error message was returned");
    }

    @Test
    void createGameNegative() throws NotAuthorizedException, DataAccessException {

        // try creating a game with invalid credentials
        assertThrowsExactly(NotAuthorizedException.class, () -> {
            CreateGameResponse response = TestFactory.createGame(false);
        }, "Create Game accepted invalid authtoken");
    }
}
