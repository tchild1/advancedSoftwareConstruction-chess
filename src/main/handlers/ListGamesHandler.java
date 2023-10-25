package handlers;

import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.ListGamesRequest;
import responses.ListGamesResponse;
import services.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler extends Handler {

    public ListGamesResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException {
        ListGamesRequest request = new ListGamesRequest(req.headers("Authorization"));
        return new ListGamesService().listGames(request);
    }
}
