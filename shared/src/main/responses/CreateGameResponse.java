package responses;

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
     */
    public CreateGameResponse(String gameID) {
        super(null);
        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
