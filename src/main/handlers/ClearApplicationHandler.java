package handlers;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import requests.ClearApplicationRequest;
import responses.ClearApplicationResponse;
import services.ClearApplicationService;
import spark.Request;
import spark.Response;

public class ClearApplicationHandler extends Handler {

    public ClearApplicationResponse handleRequest(Request req, Response res) throws DataAccessException {
        ClearApplicationRequest request = new Gson().fromJson(req.body(), ClearApplicationRequest.class);
        return new ClearApplicationService().clearApplication(request);
    }
}
