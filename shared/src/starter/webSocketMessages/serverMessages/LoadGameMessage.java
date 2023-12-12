package webSocketMessages.serverMessages;

import models.Game;


public class LoadGameMessage extends ServerMessage {

    private Game game;

    public LoadGameMessage(Game game) {
        super(ServerMessageType.LOAD_GAME);

        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
