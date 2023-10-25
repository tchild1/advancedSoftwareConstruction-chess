package handlers;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import services.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends Handler {

    public JoinGameResponse handleRequest(Request req, Response res) throws DataAccessException, BadRequestException, NotAuthorizedException, ForbiddenException {
        JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
        request.setAuthToken(req.headers("Authorization"));
        return new JoinGameService().joinGame(request);
    }
}
