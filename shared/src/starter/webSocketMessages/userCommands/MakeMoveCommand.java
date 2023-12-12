package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    String gameID;

    ChessMove move;

    public MakeMoveCommand(String authToken, String gameID, ChessMove move, String userName) {
        super(authToken);

        commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
        this.setUserName(userName);
    }

    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
