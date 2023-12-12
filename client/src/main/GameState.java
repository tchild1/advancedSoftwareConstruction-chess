import chess.Board;
import chess.ChessGame;
import chess.Game;

public class GameState {

    private String tokenString;

    private Game currBoard;

    private WSFacade gameConnection;

    private String userName;

    private ChessGame.TeamColor color;

    private String gameID;

    public GameState() {
        String tokenString = null;
        Game currBoard = null;
        WSFacade gameConnection = null;
        String userName = null;
        ChessGame.TeamColor color = null;
        String gameID = null;
    }

    public String getTokenString() {
        return tokenString;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }

    public void setColor(ChessGame.TeamColor color) {
        this.color = color;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public Game getCurrBoard() {
        return currBoard;
    }

    public void setCurrBoard(Game game) {
        this.currBoard = game;
    }

    public WSFacade getGameConnection() {
        return gameConnection;
    }

    public void setGameConnection(WSFacade gameConnection) {
        this.gameConnection = gameConnection;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
