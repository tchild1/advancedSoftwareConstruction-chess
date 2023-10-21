package models;

import chess.ChessGame;

/**
 * class representing a game and all of its information
 */
public class Game {

    /**
     * uid of the game
     */
    int gameID;

    /**
     * Username of the player playing white
     */
    String whiteUsername;

    /**
     * Username of the player playing black
     */
    String blackUsername;

    /**
     * name of the game
     */
    String gameName;

    /**
     * ChessGame object with Rules and board storage
     */
    ChessGame game;

    /**
     * Constructor for games
     *
     * @param gameID uid of game
     * @param whiteUsername username of white player
     * @param blackUsername username of black player
     * @param gameName name of this game
     * @param game board and logic of the chess game
     */
    public Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }
}
