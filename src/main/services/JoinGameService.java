package services;

import requests.JoinGameRequest;
import resposnses.JoinGameResponse;

/**
 * service for a request to join a game
 */
public class JoinGameService {

    /**
     * Verifies that the specified game exists, and, if a color is specified,
     * adds the caller as the requested color to the game If no color is
     * specified the user is joined as an observer.
     *
     * @param request object with player color and requested gameID
     * @return response with success or failure message
     */
    public JoinGameResponse joinGame(JoinGameRequest request) {
        return null;
    }
}
