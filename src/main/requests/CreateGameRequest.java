package requests;

import models.AuthToken;

/**
 * class representing a request to create a new game
 */
public class CreateGameRequest extends Request {

    /**
     * AuthToken authorizing this request
     */
    AuthToken authToken;

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
    public CreateGameRequest(AuthToken authToken, String gameName) {
        super(RequestMethods.POST);
        this.authToken = authToken;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
