package handlers;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import requests.ClearApplicationRequest;
import responses.ClearApplicationResponse;
import services.ClearApplicationService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

/**
 * Handler class for ClearApplication Requests
 */
public class ClearApplicationHandler extends Handler {

    /**
     * converts JSON to a request and passes to the service
     *
     * @param req request object
     * @param res response object
     * @return ClearApplicationResponse object
     * @throws DataAccessException if there is an error connecting to the database
     */
    public ClearApplicationResponse handleRequest(Request req, Response res) throws DataAccessException, SQLException, dataAccess.DataAccessException {
        ClearApplicationRequest request = new Gson().fromJson(req.body(), ClearApplicationRequest.class);
        return new ClearApplicationService().clearApplication(request);
    }
}
