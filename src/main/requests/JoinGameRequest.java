package requests;

import models.AuthToken;

/**
 * class representing a request to join a game
 */
public class JoinGameRequest extends Request {

    /**
     * enum of all potential player colors
     */
    enum PlayerColor {
        WHITE,
        BLACK
    }

    /**
     * color the player requesting to join wishes to play
     */
    PlayerColor playerColor;

    /**
     * ID of the game the player wishes to join
     */
    String gameID;

    /**
     * AuthToken authorizing a user to join the game
     */
    AuthToken authToken;

    /**
     * Creates a request to join a game
     *
     * @param authToken of the user requesting to join
     * @param playerColor color the user is requesting to be
     * @param gameID of the game the user wants to join
     */
    public JoinGameRequest(AuthToken authToken, PlayerColor playerColor, String gameID) {
        super(RequestMethods.PUT);
        this.authToken = authToken;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }
}
