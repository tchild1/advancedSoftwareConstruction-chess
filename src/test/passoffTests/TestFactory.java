package passoffTests;

import chess.*;
import daos.Database;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import requests.*;
import responses.LoginResponse;
import responses.RegisterUserResponse;
import services.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used for testing your code
 * Add in code using your classes for each method for each FIXME
 */
public class TestFactory {

    static RegisterUserResponse testUser;

    static String testUsername = "tchild01";
    static String testPassword = "password";
    static String testEmail = "childtanner@gmail.com";

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard(){
		return new Board();
    }

    public static ChessGame getNewGame(){
		return new Game();
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        ChessPiece newPiece = null;
        if (type == ChessPiece.PieceType.PAWN) {
            newPiece = new PawnPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.ROOK) {
            newPiece = new RookPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.KNIGHT) {
            newPiece = new KnightPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.BISHOP) {
            newPiece = new BishopPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.KING) {
            newPiece = new KingPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.QUEEN) {
            newPiece = new QueenPiece(pieceColor);
        }
		return newPiece;
    }

    public static ChessPosition getNewPosition(Integer row, Integer col){
        return new Position(row-1, col-1);
    }

    public static ChessMove getNewMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){
		return new Move(startPosition, endPosition, promotionPiece);
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort(){
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime(){
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 3000L;
    }
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    // Below are functions I wrote to make my unit tests easy with less repeated code
    //------------------------------------------------------------------------------------------------------------------
    public static responses.CreateGameResponse createGame(boolean correctAuth) throws NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {
        CreateGameRequest createGameRequest;
        if (correctAuth) {
            createGameRequest = new CreateGameRequest(testUser.getAuthToken(), String.valueOf(CreateGameService.getCurrID()));
        } else {
            createGameRequest = new CreateGameRequest("FakeAuth", String.valueOf(CreateGameService.getCurrID()));
        }
        return new CreateGameService().createGame(createGameRequest);
    }

    public static RegisterUserResponse createTestUser() throws ForbiddenException, BadRequestException, DataAccessException, SQLException, dataAccess.DataAccessException {

        RegisterUserRequest request = new RegisterUserRequest(testUsername, testPassword, testEmail);
        testUser = new RegisterUserService().registerUser(request);
        return testUser;
    }

    public static responses.ListGamesResponse listGames(Boolean correctAuth) throws NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {
        ListGamesRequest listGamesRequest;
        if (correctAuth) {
            listGamesRequest = new ListGamesRequest(testUser.getAuthToken());
        } else {
            listGamesRequest = new ListGamesRequest("Fake AuthToken");
        }
        return new ListGamesService().listGames(listGamesRequest);
    }

    public static void clearApplication() throws DataAccessException, SQLException, dataAccess.DataAccessException {
        new ClearApplicationService().clearApplication(new ClearApplicationRequest(new AuthToken("UNIT_TESTS")));
    }

    public static void joinGame(ChessGame.TeamColor color, String gameID) throws ForbiddenException, BadRequestException, NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {
        JoinGameRequest request = new JoinGameRequest(testUser.getAuthToken(), color, gameID);
        new JoinGameService().joinGame(request);
    }

    public static LoginResponse login(boolean currectAuth) throws NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {
        LoginRequest request;
        if (currectAuth) {
            request = new LoginRequest(testUsername, testPassword);
        } else {
            request = new LoginRequest(testUsername, "WRONG_PASSWORD");
        }
        return new LoginService().login(request);
    }

    public static void logout(boolean correctAuth) throws NotAuthorizedException, DataAccessException, SQLException, dataAccess.DataAccessException {
        LogoutRequest request;
        if (correctAuth) {
            request = new LogoutRequest(testUser.getAuthToken());
        } else {
            request = new LogoutRequest("WRONG_AUTH");
        }
        new LogoutService().logout(request);
    }

    public static String getTestUsername() {
        return testUsername;
    }

    public static RegisterUserResponse getTestUser() {
        return testUser;
    }

    public static boolean GetAuthFromDB(String username) throws SQLException, dataAccess.DataAccessException {
        String sql = "SELECT * FROM chess.auth WHERE username='" + username + "';";
        ResultSet results = getFromDB(sql);

        return results.next();
    }

    private static ResultSet getFromDB(String sql) throws dataAccess.DataAccessException, SQLException {
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    public static boolean GetGameFromDB(int GameID) throws SQLException, dataAccess.DataAccessException {
        String sql = "SELECT * FROM chess.game WHERE game_id='" + String.valueOf(GameID) + "';";
        ResultSet results = getFromDB(sql);

        return results.next();
    }

    public static ResultSet GetGameInfoFromDB(int GameID) throws SQLException, dataAccess.DataAccessException {
        String sql = "SELECT * FROM chess.game WHERE game_id='" + String.valueOf(GameID) + "';";
        ResultSet results = getFromDB(sql);

        return results;
    }

    public static ResultSet GetUserInfoFromDB(String username) throws SQLException, dataAccess.DataAccessException {
        String sql = "SELECT * FROM chess.user WHERE username='" + username + "';";
        ResultSet results = getFromDB(sql);

        return results;
    }
}
