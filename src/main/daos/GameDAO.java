package daos;

import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import models.Game;
import requests.JoinGameRequest;

import java.util.ArrayList;

/**
 * This table provides access to game database tables
 */
public class GameDAO {

    public static ArrayList<Game> AllGames = new ArrayList<Game>();

    /**
     * adds a game to the Database
     *
     * @param game object to be created
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public static void CreateGame(Game game) throws DataAccessException {
        AllGames.add(game);
    }

    /**
     * deletes all games from the database
     *
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void DeleteAllGames() throws DataAccessException {
        AllGames.clear();
    }

    /**
     * Adds a user to play a game
     * @param gameID being played
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public static void AddPlayerToGame(String gameID, JoinGameRequest.PlayerColor color, String username) throws DataAccessException, ForbiddenException {
        for (Game allGame : AllGames) {
            if (String.valueOf(allGame.getGameID()).equals(gameID)) {
                if (color == JoinGameRequest.PlayerColor.BLACK) {
                    if (allGame.getBlackUsername() == null) {
                        allGame.setBlackUsername(username);
                    } else {
                        throw new ForbiddenException("Error: already taken");
                    }
                } else if (color == JoinGameRequest.PlayerColor.WHITE) {
                    if (allGame.getWhiteUsername() == null) {
                        allGame.setWhiteUsername(username);
                    } else {
                        throw new ForbiddenException("Error: already taken");
                    }
                }
            }
        }
    }

    public static void AddObserverToGame(String gameID, String username) {
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

    public static Boolean GameExists(String gameID) {
        for (Game allGame : AllGames) {
            if (String.valueOf(allGame.getGameID()).equals(gameID)) {
                return true;
            }
        }
        return false;
    }
}
