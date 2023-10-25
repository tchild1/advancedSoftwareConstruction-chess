package handlers;

import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.LogoutRequest;
import responses.LogoutResponse;
import services.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler extends Handler {

    public LogoutResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException {
        LogoutRequest request = new LogoutRequest(req.headers("Authorization"));
        return new LogoutService().logout(request);
    }
}
