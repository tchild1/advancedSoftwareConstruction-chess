package responses;

import models.Game;

import java.util.ArrayList;

/**
 * response to a request to list all games currently being played
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
     */
    public ListGamesResponse(ArrayList<Game> games) {
        super(null);
        this.games = games;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
