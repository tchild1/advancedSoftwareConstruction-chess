package handlers;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends Handler {

    public CreateGameResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException {
        CreateGameRequest request = new Gson().fromJson(req.body(), CreateGameRequest.class);
        request.setAuthToken(req.headers("Authorization"));
        return new CreateGameService().createGame(request);
    }
}
