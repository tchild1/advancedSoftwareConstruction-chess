package services;

import daos.AuthDAO;
import exceptions.DataAccessException;
import daos.GameDAO;
import exceptions.NotAuthorizedException;
import requests.ListGamesRequest;
import responses.ListGamesResponse;

/**
 * service for a request to list all current games
 */
public class ListGamesService {

    /**
     * Gives a list of all games after checking the user's authorization.
     *
     * @param request request with AuthToken
     * @return response with a list of games
     */
    public ListGamesResponse listGames(ListGamesRequest request) throws DataAccessException, NotAuthorizedException {
        if (AuthDAO.IsAuthorized(request.getAuthToken())) {
            return new ListGamesResponse(GameDAO.GetAllGames());
        } else {
            throw new NotAuthorizedException("Error: not authorized");
        }
    }
}
