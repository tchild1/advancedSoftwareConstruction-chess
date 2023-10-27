package handlers;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import requests.RegisterUserRequest;
import com.google.gson.Gson;
import responses.RegisterUserResponse;
import services.RegisterUserService;
import spark.Request;
import spark.Response;

/**
 * Handler for all requests to Register a new user
 */
public class RegisterUserHandler extends Handler {

    /**
     * Converts JSON to request object and passes request to service
     *
     * @param req Spark request
     * @param res Spark response
     * @return RegisterUserResponse object
     * @throws DataAccessException if there is a problem connecting to the Database
     * @throws BadRequestException if the Request is invalid
     * @throws ForbiddenException if the Request is forbidden
     */
    public RegisterUserResponse handleRequest(Request req, Response res) throws DataAccessException, ForbiddenException, BadRequestException {
        RegisterUserRequest request = new Gson().fromJson(req.body(), RegisterUserRequest.class);
        return new RegisterUserService().registerUser(request);
    }
}
