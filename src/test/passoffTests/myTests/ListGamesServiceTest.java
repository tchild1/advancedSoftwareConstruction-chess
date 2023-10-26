package passoffTests.myTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import responses.ListGamesResponse;
import services.*;
import static org.junit.jupiter.api.Assertions.*;

class ListGamesServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException, NotAuthorizedException {
        TestFactory.clearApplication();

        // create test user for Authorization
        TestFactory.createTestUser();

        // create games to list
        TestFactory.createGame(true);
        TestFactory.createGame(true);
    }

    @Test
    void listGamesPositive() throws NotAuthorizedException, DataAccessException, ForbiddenException, BadRequestException {
        ListGamesResponse response = TestFactory.listGames(true);

        assertEquals(String.valueOf(response.getGames().get(0).getGameID()), String.valueOf(CreateGameService.getCurrID()-1), "ListGames returned incorrect game ID");
        assertNull(response.getGames().get(0).getWhiteUsername(), "White user should be null, but returned a value");
        assertNull(response.getGames().get(0).getBlackUsername(), "Black user should be null, but returned a value");

        assertEquals(String.valueOf(response.getGames().get(1).getGameID()), String.valueOf(CreateGameService.getCurrID()), "ListGames returned incorrect game ID");
        assertNull(response.getGames().get(1).getWhiteUsername(), "White user should be null, but returned a value");
    }

    @Test
    void listGamesNegative() throws NotAuthorizedException, DataAccessException {

        // test with invalid authorization
        assertThrowsExactly(NotAuthorizedException.class, () -> {
            TestFactory.listGames(false);
        }, "List Games accepted invalid AuthToken");
    }
}