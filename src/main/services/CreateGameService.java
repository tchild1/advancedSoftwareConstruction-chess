package services;

import daos.AuthDAO;
import exceptions.DataAccessException;
import daos.GameDAO;
import exceptions.NotAuthorizedException;
import models.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * service for a request to create a new game
 */
public class CreateGameService {
    static private int currID;

    /**
     * Creates a new game after checking a user's AuthToken
     *
     * @param request object with AuthToken
     * @return response object with gameID
     */
    public CreateGameResponse createGame(CreateGameRequest request) throws DataAccessException, NotAuthorizedException {
        if (AuthDAO.IsAuthorized(request.getAuthToken())) {
            int gameID = GenerateGameID();
            Game game = new Game(gameID, null, null, request.getGameName());
            GameDAO.CreateGame(game);
            return new CreateGameResponse(String.valueOf(gameID));
        } else {
            throw new NotAuthorizedException("Error: not authorized");
        }
    }

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
}
