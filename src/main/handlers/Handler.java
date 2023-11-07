package handlers;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

/**
 * Abstract parent class for all handler objects
 */
public abstract class Handler {

    /**
     * Method to handle ALL requests from the server and catch exceptions
     *
     * @param req Spark request
     * @param res Spark response
     * @return JSON response to server
     */
    public String handler(Request req, Response res) {
        res.status(200);
        responses.Response response;
        try {
            response = handleRequest(req, res);
        } catch (DataAccessException | dataAccess.DataAccessException e) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Gson().toJson(response);
    }

    /**
     * Abstract method implemented in all handler classes to convert JSON to a request and pass to
     * respective service classes
     *
     * @param req Spark request
     * @param res Spark response
     * @return response object
     * @throws DataAccessException if there is an error connecting to the database
     * @throws NotAuthorizedException if the user is not authorized to make this request
     * @throws ForbiddenException if the user makes a forbidden request
     * @throws BadRequestException if the user's request is invalid
     */
    protected abstract responses.Response handleRequest(Request req, Response res)
            throws DataAccessException, NotAuthorizedException, ForbiddenException, BadRequestException, SQLException, dataAccess.DataAccessException;
}
