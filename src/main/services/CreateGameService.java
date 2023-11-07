package services;

import daos.AuthDAO;
import exceptions.DataAccessException;
import daos.GameDAO;
import exceptions.NotAuthorizedException;
import models.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

import java.sql.SQLException;

/**
 * service for a request to create a new game
 */
public class CreateGameService {

    /**
     * Current Game ID (increments every game creation)
     */
    static public int currID;

    /**
     * Creates a new game after checking a user's AuthToken
     *
     * @param request object with AuthToken
     * @return response object with gameID
     */
    public CreateGameResponse createGame(CreateGameRequest request) throws DataAccessException, NotAuthorizedException, dataAccess.DataAccessException, SQLException {
        if (AuthDAO.IsAuthorized(request.getAuthToken())) {
            Game game = new Game(0, null, null, request.getGameName());
            int gameID = GameDAO.CreateGame(game);
            return new CreateGameResponse(String.valueOf(gameID));
        } else {
            throw new NotAuthorizedException("Error: not authorized");
        }
    }

    /**
     * Function that generates a new Game ID
     * @return current Game ID
     */
    private int GenerateGameID() {
        if (currID == 9999) {
            currID = 1111;
            return currID;
        } else if (currID == 0) {
            currID = 1111;
            return  currID;
        }else {
            currID++;
            return currID;
        }
    }

    public static int getCurrID() {
        return currID;
    }
}
