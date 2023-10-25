package services;

import daos.AuthDAO;
import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.LogoutRequest;
import responses.LogoutResponse;

/**
 * service for a request to logout
 */
public class LogoutService {

    /**
     * Logs out the user represented by the AuthToken
     *
     * @param request object with User's AuthToken
     * @return object with message if failure
     */
    public LogoutResponse logout(LogoutRequest request) throws DataAccessException, NotAuthorizedException {
        if (AuthDAO.IsAuthorized(request.getAuthToken())) {
            new AuthDAO().DeleteAuthToken(request.getAuthToken());
            return new LogoutResponse();
        } else {
            throw new NotAuthorizedException("Error: not unauthorized");
        }
    }
}
