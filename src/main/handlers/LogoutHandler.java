package handlers;

import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.LogoutRequest;
import responses.LogoutResponse;
import services.LogoutService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class LogoutHandler extends Handler {

    /**
     * Converts JSON to request object and passes request to service
     *
     * @param req Spark request
     * @param res Spark response
     * @return LogoutResponse object
     * @throws DataAccessException if there is a problem connecting to the Database
     * @throws NotAuthorizedException if the request does not have proper authorization
     */
    public LogoutResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException, SQLException, dataAccess.DataAccessException {
        LogoutRequest request = new LogoutRequest(req.headers("Authorization"));
        return new LogoutService().logout(request);
    }
}
