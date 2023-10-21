package services;

import requests.ListGamesRequest;
import resposnses.ListGamesResponse;

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
    public ListGamesResponse listGames(ListGamesRequest request) {
        return null;
    }
}
