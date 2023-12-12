package webSocketMessages.serverMessages;

import models.Game;

public class ErrorMessage extends ServerMessage {

    private String errorMessage;

    public ErrorMessage(String message) {
        super(ServerMessageType.ERROR);
        this.errorMessage = message;
    }

    public String getMessage() {
        return errorMessage;
    }

    public void setMessage(String message) {
        this.errorMessage = message;
    }

}
