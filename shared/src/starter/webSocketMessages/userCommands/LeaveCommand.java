package webSocketMessages.userCommands;


import chess.ChessGame;

import java.util.UUID;

public class LeaveCommand extends UserGameCommand {

    private String gameID;

    private ChessGame.TeamColor color;

    public LeaveCommand(String authToken, String gameId) {
        super(authToken);
        commandType = CommandType.LEAVE;
        this.gameID = gameId;
    }

    public String getGameId() {
        return gameID;
    }

    public void setGameId(String gameId) {
        this.gameID = gameId;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }

    public void setColor(ChessGame.TeamColor color) {
        this.color = color;
    }
}
