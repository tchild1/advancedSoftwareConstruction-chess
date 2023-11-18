package requests;

import chess.ChessGame;

/**
 * class representing a request to join a game
 */
public class JoinGameRequest {

    /**
     * color the player requesting to join wishes to play
     */
    ChessGame.TeamColor playerColor;

    /**
     * ID of the game the player wishes to join
     */
    String gameID;

    /**
     * AuthToken authorizing a user to join the game
     */
    String authToken;

    /**
     * Creates a request to join a game
     *
     * @param authToken of the user requesting to join
     * @param playerColor color the user is requesting to be
     * @param gameID of the game the user wants to join
     */
    public JoinGameRequest(String authToken, ChessGame.TeamColor playerColor, String gameID) {
        this.authToken = authToken;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
