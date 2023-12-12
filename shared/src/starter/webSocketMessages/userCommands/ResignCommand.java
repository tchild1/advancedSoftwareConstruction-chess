package webSocketMessages.userCommands;

import chess.ChessGame;

public class ResignCommand extends UserGameCommand {

    private String gameID;

    private ChessGame.TeamColor color;

    public ResignCommand(String authToken, String gameId, ChessGame.TeamColor color) {
        super(authToken);
        commandType = CommandType.RESIGN;
        this.gameID = gameId;
        this.color = color;
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

