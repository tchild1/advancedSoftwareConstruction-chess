package daos;

import chess.ChessGame;
import com.google.gson.GsonBuilder;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static daos.GameDAO.*;
import static org.junit.jupiter.api.Assertions.*;
import static passoffTests.TestFactory.*;

class GameDAOTest {

    @BeforeEach
    void setUp() throws SQLException, DataAccessException, dataAccess.DataAccessException, ForbiddenException, BadRequestException {
        clearApplication();
        createTestUser();
    }

    @Test
    void createGamePositive() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");

        int GameID = CreateGame(game);

        assertTrue(GetGameFromDB(GameID));
    }

    @Test
    void createGameNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");

        CreateGame(game);

        assertFalse(GetGameFromDB(7));
    }

    @Test
    void Clear() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");

        int GameID = CreateGame(game);
        GameDAO.DeleteAllGames();

        assertFalse(GetGameFromDB(GameID));
    }

    @Test
    void addPlayerToGamePositive() throws SQLException, DataAccessException, dataAccess.DataAccessException, ForbiddenException {
        Game game = new Game(0, "", "", "TestGame");
        int GameID = CreateGame(game);

        String testUser = "TestUser";
        AddPlayerToGame(String.valueOf(GameID), ChessGame.TeamColor.WHITE, testUser);

        ResultSet results = GetGameInfoFromDB(GameID);
        if (results.next()) {
            assertEquals(results.getString(5), testUser, "Incorrect username was returned");
        }
    }

    @Test
    void addPlayerToGameNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException, ForbiddenException {
        Game game = new Game(0, "", "", "TestGame");
        int GameID = CreateGame(game);

        String testUser = "TestUser";
        AddPlayerToGame(String.valueOf(GameID), ChessGame.TeamColor.WHITE, testUser);

        ResultSet results = GetGameInfoFromDB(GameID);
        if (results.next()) {
            assertNotEquals(results.getString(4), testUser, "Player added to wrong color");
        }
    }

    @Test
    void addObserverToGamePositive() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");
        int GameID = CreateGame(game);
        String username = "testuser";

        AddObserverToGame(String.valueOf(GameID), username);

        ResultSet results = GetGameInfoFromDB(GameID);
        if (results.next()) {
            String json = results.getString(2);

            GsonBuilder builder = createBuilder();

            models.Game DBgame = builder.create().fromJson(json, models.Game.class);

            assertEquals(DBgame.getObservers().get(0), username, "User was not added correctly");
        }
    }

    @Test
    void addObserverToGameNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");
        int GameID = CreateGame(game);
        String username = "testuser";

        AddObserverToGame(String.valueOf(String.valueOf(0)), username);

        ResultSet results = GetGameInfoFromDB(GameID);
        if (results.next()) {
            String json = results.getString(2);

            GsonBuilder builder = createBuilder();

            models.Game DBgame = builder.create().fromJson(json, models.Game.class);

            assertEquals(DBgame.getObservers(), new ArrayList<>(), "User should not have been added, wrong ID Used");
        }
    }

    @Test
    void getAllGamesPositive() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");
        int GameID = CreateGame(game);

        Game game2 = new Game(0, "", "", "TestGame");
        int GameID2 = CreateGame(game);

        assertEquals(GetAllGames().size(), 2, "List of all games is wrong size.");
    }

    @Test
    void getAllGamesNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");
        int GameID = CreateGame(game);

        Game game2 = new Game(0, "", "", "TestGame");
        int GameID2 = CreateGame(game);

        assertFalse(GetAllGames().size() == 1);
    }

    @Test
    void gameExistsPositive() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");
        int GameID = CreateGame(game);

        assertTrue(GameExists(String.valueOf(GameID)), "Game with this ID should exist");
    }

    @Test
    void gameExistsNegative() throws SQLException, DataAccessException, dataAccess.DataAccessException {
        Game game = new Game(0, "", "", "TestGame");

        assertFalse(GameExists(String.valueOf(0)), "Game with ID=0 should not exist");
    }
}