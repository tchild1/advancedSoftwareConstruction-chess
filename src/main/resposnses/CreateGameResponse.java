package resposnses;

/**
 * response to a request to create a new game
 */
public class CreateGameResponse extends Response {

    /**
     * ID of the game created
     */
    String gameID;

    /**
     * Constructor creating game response
     *
     * @param gameID id of the game created
     * @param message message if game failed to create
     * @param success boolean stating success/failure of request
     */
    public CreateGameResponse(String gameID, String message, boolean success) {
        super(message, success);
        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
