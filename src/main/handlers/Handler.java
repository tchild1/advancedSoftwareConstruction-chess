package handlers;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import spark.Request;
import spark.Response;

public abstract class Handler {

    public String handler(Request req, Response res) {
        res.status(200);
        responses.Response response;
        try {
            response = handleRequest(req, res);
        } catch (DataAccessException e) {
            res.status();
            response = new responses.Response(e.getMessage());
        } catch (NotAuthorizedException e) {
            res.status(401);
            response = new responses.Response(e.getMessage());
        } catch (ForbiddenException e) {
            res.status(403);
            response = new responses.Response(e.getMessage());
        } catch (BadRequestException e) {
            res.status(400);
            response = new responses.Response(e.getMessage());
        }
        return new Gson().toJson(response);
    }

    protected abstract responses.Response handleRequest(Request req, Response res) throws DataAccessException, NotAuthorizedException, ForbiddenException, BadRequestException;
}
