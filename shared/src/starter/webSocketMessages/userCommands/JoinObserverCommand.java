package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinObserverCommand extends UserGameCommand {

    String gameID;
    public JoinObserverCommand(String authToken) {
        super(authToken);

        commandType = CommandType.JOIN_OBSERVER;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
