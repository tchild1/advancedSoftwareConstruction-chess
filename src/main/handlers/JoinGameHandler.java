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

import java.sql.SQLException;

/**
 * Handler for all requests to join a game
 */
public class JoinGameHandler extends Handler {

    /**
     * Converts JSON to request object and passes request to service
     *
     * @param req Spark request
     * @param res Spark response
     * @return JoinGameResponse object
     * @throws DataAccessException if there is a problem connecting to the Database
     * @throws BadRequestException if the Request is invalid
     * @throws NotAuthorizedException if the request does not have proper authorization
     * @throws ForbiddenException if the Request is forbidden
     */
    public JoinGameResponse handleRequest(Request req, Response res) throws DataAccessException, BadRequestException, NotAuthorizedException, ForbiddenException, SQLException, dataAccess.DataAccessException {
        JoinGameRequest request = new Gson().fromJson(req.body(), JoinGameRequest.class);
        request.setAuthToken(req.headers("Authorization"));
        return new JoinGameService().joinGame(request);
    }
}
