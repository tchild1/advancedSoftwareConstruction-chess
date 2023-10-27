package services;

import daos.AuthDAO;
import exceptions.DataAccessException;
import daos.GameDAO;
import exceptions.BadRequestException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import requests.JoinGameRequest;
import responses.JoinGameResponse;

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
    public JoinGameResponse joinGame(JoinGameRequest request) throws DataAccessException, NotAuthorizedException, BadRequestException, ForbiddenException {
        // check user is authorized
        if (AuthDAO.IsAuthorized(request.getAuthToken())) {
            // check the game exists
            if (GameDAO.GameExists(request.getGameID())) {
                String username = AuthDAO.GetUsername(request.getAuthToken());
                // if not color, add as an observer
                if (request.getPlayerColor() != null) {
                    GameDAO.AddPlayerToGame(request.getGameID(), request.getPlayerColor(), username);
                } else {
                    GameDAO.AddObserverToGame(request.getGameID(), username);
                }
                return new JoinGameResponse();
            } else {
                throw new BadRequestException("Error: bad request");
            }
        } else {
            throw new NotAuthorizedException("Error: not authorized");
        }
    }
}
