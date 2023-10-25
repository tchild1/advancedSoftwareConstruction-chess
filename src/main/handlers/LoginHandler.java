package handlers;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import exceptions.NotAuthorizedException;
import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler extends Handler {

    public LoginResponse handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException {
        LoginRequest request = new Gson().fromJson(req.body(), LoginRequest.class);
        return new LoginService().login(request);
    }
}
