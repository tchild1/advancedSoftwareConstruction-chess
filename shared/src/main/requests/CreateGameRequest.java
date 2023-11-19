package requests;

/**
 * class representing a request to create a new game
 */
public class CreateGameRequest extends Request {

    /**
     * AuthToken authorizing this request
     */
    String authToken;

    /**
     * name of the game to be created
     */
    String gameName;

    /**
     * Constructor for creating a game request
     *
     * @param authToken of user attempting to create a game
     * @param gameName name of the game being created
     */
    public CreateGameRequest(String authToken, String gameName) {
        this.authToken = authToken;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
