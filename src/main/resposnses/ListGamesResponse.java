package resposnses;

import models.Game;

import java.util.ArrayList;

/**
 * response to a requset to list all games currently being played
 */
public class ListGamesResponse extends Response {

    /**
     * a list of all games being played
     */
    ArrayList<Game> games;

    /**
     * Constructor for response of request to list all games
     *
     * @param games array of all games currently being played
     * @param message describing failure (if applicable)
     * @param success boolean value stating success or failure of request
     */
    public ListGamesResponse(ArrayList<Game> games, String message, boolean success) {
        super(message, success);
        this.games = games;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
