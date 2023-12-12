package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand {

    String gameID;

    ChessGame.TeamColor playerColor;

    public JoinPlayerCommand(String authToken, String gameID, ChessGame.TeamColor playerColor) {
        super(authToken);

        commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public String getGameID() {
        return gameID;
    }
}
