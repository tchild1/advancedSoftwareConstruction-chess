package daos;

import models.Game;
import models.User;

import java.util.ArrayList;

/**
 * This table provides access to game database tables
 */
public class GameDAO {

    /**
     * adds a game to the Database
     *
     * @param gameName name of the game to be added
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void CreateGame(String gameName) throws DataAccessException {}

    /**
     * deletes a game from the database
     *
     * @param game to be deleted
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void DeleteGame(Game game) throws DataAccessException {}

    /**
     * deletes all games from the database
     *
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void DeleteAllGames() throws DataAccessException {}

    /**
     * Updates a currently existing game in the database
     * @param game being played
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public void UpdateGame(Game game) throws DataAccessException {}

    /**
     * Gets a game from the database
     *
     * @param gameID id of the game to be retrieved
     * @return a game
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public Game GetGame(String gameID) throws DataAccessException {
        return null;
    }

    /**
     * Gets all current games from the database
     *
     * @return a list of all games being played
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public ArrayList<Game> GetAllGames() throws DataAccessException {
        return null;
    }

    /**
     * A method for claiming a spot in the game.
     * The player's username is provided and should be saved as
     * either the whitePlayer or blackPlayer in the database.
     *
     * @param user object with all the user's information
     */
    public void ClaimSpot(User user) throws DataAccessException {}
}
