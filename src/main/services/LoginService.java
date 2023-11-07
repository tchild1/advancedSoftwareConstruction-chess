package services;

import daos.AuthDAO;
import exceptions.DataAccessException;
import daos.UserDAO;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import requests.LoginRequest;
import responses.LoginResponse;

import java.sql.SQLException;

/**
 * service for a request to login
 */
public class LoginService {

    /**
     * This method logs in an existing user
     *
     * @param request object with username and password of user
     * @return a response with a new AuthToken
     */
    public LoginResponse login(LoginRequest request) throws DataAccessException, NotAuthorizedException, SQLException, dataAccess.DataAccessException {
         if (new UserDAO().AuthenticateUser(request.getUsername(), request.getPassword())) {
             AuthToken token = new AuthToken(request.getUsername());
//             AuthDAO.AddAuthToken(token);
             return new LoginResponse(token.getAuthToken(), request.getUsername());
         } else {
            throw new NotAuthorizedException("Error: unauthorized");
         }
    }
}
