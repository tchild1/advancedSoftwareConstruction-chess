package daos;

import chess.ChessGame;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import models.Game;
import requests.JoinGameRequest;

import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * This table provides access to game database tables
 */
public class GameDAO {

    /**
     * This Static variable has a list of all games currently being played
     */
    public static ArrayList<Game> AllGames = new ArrayList<Game>();

    /**
     * Adds a game to the Database
     *
     * @param game object to be added
     * @throws DataAccessException if there is an error accessing database, exception is thrown
     */
    public static void CreateGame(Game game) throws DataAccessException {
        AllGames.add(game);
    }

    /**
     * Deletes all games from the database
     *
     * @throws DataAccessException if there is an error accessing the database, exception is thrown
     */
    public void DeleteAllGames() throws DataAccessException {
        AllGames.clear();
    }

    /**
     * Adds a user to a current game
     *
     * @param gameID being played
     * @throws DataAccessException if there is an error accessing the database, exception is thrown
     */
    public static void AddPlayerToGame(String gameID, ChessGame.TeamColor color, String username) throws DataAccessException, ForbiddenException {
        for (Game allGame : AllGames) {
            if (String.valueOf(allGame.getGameID()).equals(gameID)) {
                if (color == ChessGame.TeamColor.BLACK) {
                    if (allGame.getBlackUsername() == null) {
                        allGame.setBlackUsername(username);
                    } else {
                        throw new ForbiddenException("Error: already taken");
                    }
                } else if (color == ChessGame.TeamColor.WHITE) {
                    if (allGame.getWhiteUsername() == null) {
                        allGame.setWhiteUsername(username);
                    } else {
                        throw new ForbiddenException("Error: already taken");
                    }
                }
            }
        }
    }

    /**
     * Adds a user as an observer to a game
     *
     * @param gameID ID of Game that the user wants to observe
     * @param username of the Observer
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public static void AddObserverToGame(String gameID, String username) throws DataAccessException {
        for (Game game : AllGames) {
            if (String.valueOf(game.getGameID()).equals(gameID)) {
                game.addObservers(username);
            }
        }
    }

    /**
     * Gets all current games from the database
     *
     * @return a list of all games being played
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public static ArrayList<Game> GetAllGames() throws DataAccessException {
        return AllGames;
    }

    /**
     * Returns True if a game with this ID exists, false otherwise
     *
     * @param gameID ID of the game to check if it exists
     * @return Boolean value
     */
    public static Boolean GameExists(String gameID) {
        for (Game allGame : AllGames) {
            if (String.valueOf(allGame.getGameID()).equals(gameID)) {
                return true;
            }
        }
        return false;
    }
}
