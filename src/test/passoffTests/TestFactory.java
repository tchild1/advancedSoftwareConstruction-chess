package passoffTests;

import chess.*;
import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import exceptions.NotAuthorizedException;
import models.AuthToken;
import requests.*;
import responses.JoinGameResponse;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterUserResponse;
import services.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    public static responses.CreateGameResponse createGame(boolean correctAuth) throws NotAuthorizedException, DataAccessException {
        if (correctAuth) {
            CreateGameRequest createGameRequest = new CreateGameRequest(testUser.getAuthToken(), String.valueOf(CreateGameService.getCurrID()));
            return new CreateGameService().createGame(createGameRequest);
        } else {
            CreateGameRequest createGameRequest = new CreateGameRequest("FakeAuth", String.valueOf(CreateGameService.getCurrID()));
            return new CreateGameService().createGame(createGameRequest);
        }
    }

    public static RegisterUserResponse createTestUser() throws ForbiddenException, BadRequestException, DataAccessException {

        RegisterUserRequest request = new RegisterUserRequest(testUsername, testPassword, testEmail);
        testUser = new RegisterUserService().registerUser(request);
        return testUser;
    }

    public static responses.ListGamesResponse listGames(Boolean correctAuth) throws NotAuthorizedException, DataAccessException {
        if (correctAuth) {
            ListGamesRequest listGamesRequest = new ListGamesRequest(testUser.getAuthToken());
            return new ListGamesService().listGames(listGamesRequest);
        } else {
            ListGamesRequest listGamesRequest = new ListGamesRequest("Fake AuthToken");
            return new ListGamesService().listGames(listGamesRequest);
        }
    }

    public static void clearApplication() throws DataAccessException {
        new ClearApplicationService().clearApplication(new ClearApplicationRequest(new AuthToken("UNIT_TESTS")));
    }

    public static JoinGameResponse joinGame(JoinGameRequest.PlayerColor color) throws ForbiddenException, BadRequestException, NotAuthorizedException, DataAccessException {
        JoinGameRequest request = new JoinGameRequest(testUser.getAuthToken(), color, String.valueOf(CreateGameService.getCurrID()));
        return new JoinGameService().joinGame(request);
    }

    public static LoginResponse login(boolean currectAuth) throws NotAuthorizedException, DataAccessException {
        if (currectAuth) {
            LoginRequest request = new LoginRequest(testUsername, testPassword);
            return new LoginService().login(request);
        } else {
            LoginRequest request = new LoginRequest(testUsername, "WRONG_PASSWORD");
            return new LoginService().login(request);
        }
    }

    public static LogoutResponse logout(boolean correctAuth) throws NotAuthorizedException, DataAccessException {
        if (correctAuth) {
            LogoutRequest request = new LogoutRequest(testUser.getAuthToken());
            return new LogoutService().logout(request);
        } else {
            LogoutRequest request = new LogoutRequest("WRONG_AUTH");
            return new LogoutService().logout(request);
        }

    }

    public static String getTestUsername() {
        return testUsername;
    }

    public static String getTestEmail() {
        return testEmail;
    }

    public static String getTestPassword() {
        return testPassword;
    }

    public static RegisterUserResponse getTestUser() {
        return testUser;
    }
}
