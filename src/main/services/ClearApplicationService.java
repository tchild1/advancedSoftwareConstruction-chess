package services;

import daos.AuthDAO;
import exceptions.DataAccessException;
import daos.GameDAO;
import daos.UserDAO;
import requests.ClearApplicationRequest;
import responses.ClearApplicationResponse;

import java.sql.SQLException;

/**
 * service for requests to clear all application data
 */
public class ClearApplicationService {

    /**
     * This method checks the user's AuthToken,
     * then if authorized connects to the DAOs and clears all users, games, and authTokens.
     *
     * @param request object with AuthToken and method of the request
     * @return returns a response object
     */
    public ClearApplicationResponse clearApplication(ClearApplicationRequest request) throws DataAccessException, SQLException, dataAccess.DataAccessException {
        new UserDAO().DeleteAllUsers();
        new AuthDAO().DeleteAllAuthTokens();
        new GameDAO().DeleteAllGames();
        return new ClearApplicationResponse();
    }
}
