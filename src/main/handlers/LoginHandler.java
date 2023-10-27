package handlers;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

/**
 * Handler for all requests to log in
 */
public class LoginHandler extends Handler {

    /**
     * Converts JSON to request object and passes request to service
     *
     * @param req Spark request
     * @param res Spark response
     * @return LoginResponse object
     * @throws DataAccessException if there is a problem connecting to the Database
     * @throws NotAuthorizedException if the request does not have proper authorization
     */
    public LoginResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException {
        LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
        return new LoginService().login(request);
    }
}
