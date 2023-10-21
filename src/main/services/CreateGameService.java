package services;

import requests.CreateGameRequest;
import resposnses.CreateGameResponse;

/**
 * service for a request to create a new game
 */
public class CreateGameService {

    /**
     * Creates a new game after checking a user's AuthToken
     *
     * @param request object with AuthToken
     * @return response object with gameID
     */
    public CreateGameResponse createGame(CreateGameRequest request) {
        return null;
    }
}
