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

public class RegisterUserHandler extends Handler {

    public RegisterUserResponse handleRequest(Request req, Response res) throws DataAccessException, ForbiddenException, BadRequestException {
        RegisterUserRequest request = new Gson().fromJson(req.body(), RegisterUserRequest.class);
        return new RegisterUserService().registerUser(request);
    }
}
