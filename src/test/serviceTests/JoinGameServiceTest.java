package serviceTests;

import chess.ChessGame;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import responses.CreateGameResponse;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JoinGameServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException, ForbiddenException, BadRequestException, NotAuthorizedException, SQLException, dataAccess.DataAccessException {
        // Get a fresh start
        TestFactory.clearApplication();

        // create test user for Authorization
        TestFactory.createTestUser();
    }

    @Test
    void joinGamePositive() throws ForbiddenException, BadRequestException, NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {

        // Create a game to join
        CreateGameResponse game = TestFactory.createGame(true);

        // joining a game should not throw an exception
        assertDoesNotThrow(() -> {
            TestFactory.joinGame(ChessGame.TeamColor.WHITE, game.getGameID());
        }, "Joining a game threw an error");
    }

    @Test
    void joinGameNegative() throws ForbiddenException, BadRequestException, NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {
        // Create a game to join
        CreateGameResponse game = TestFactory.createGame(true);

        TestFactory.joinGame(ChessGame.TeamColor.WHITE, game.getGameID());

        // try adding a second user to the same color
        assertThrowsExactly(ForbiddenException.class, () -> {
            TestFactory.joinGame(ChessGame.TeamColor.WHITE, game.getGameID());
        }, "Should have thrown an error when trying to add two users to play white");
    }
}