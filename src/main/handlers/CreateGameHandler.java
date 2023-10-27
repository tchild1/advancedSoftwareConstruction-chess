package handlers;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.CreateGameService;
import spark.Request;
import spark.Response;

/**
 * Handler class for requests to Create a new Game
 */
public class CreateGameHandler extends Handler {

    /**
     * Converts JSON to a request and passes request to the service
     *
     * @param req Spark request
     * @param res Spark response
     * @return CreateGameResponse Object
     * @throws DataAccessException if there is an error accessing the Database
     * @throws NotAuthorizedException if the user is not authorized
     */
    public CreateGameResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException {
        CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
        request.setAuthToken(req.headers("Authorization"));
        return new CreateGameService().createGame(request);
    }
}
