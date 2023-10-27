package handlers;

import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.ListGamesRequest;
import responses.ListGamesResponse;
import services.ListGamesService;
import spark.Request;
import spark.Response;

/**
 * Handler for requests to ListGames
 */
public class ListGamesHandler extends Handler {

    /**
     * Converts JSON to request object and passes request to service
     *
     * @param req Spark request
     * @param res Spark response
     * @return ListGamesResponse object
     * @throws DataAccessException if there is a problem connecting to the Database
     * @throws NotAuthorizedException if the request does not have proper authorization
     */
    public ListGamesResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException {
        ListGamesRequest request = new ListGamesRequest(req.headers("Authorization"));
        return new ListGamesService().listGames(request);
    }
}
