import chess.Board;
import chess.ChessGame;

public class GameState {

    private String tokenString;

    private Board currBoard;

    private WSFacade gameConnection;

    private String userName;

    private ChessGame.TeamColor color;

    private String gameID;

    public GameState() {
        String tokenString = null;
        Board currBoard = null;
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

    public Board getCurrBoard() {
        return currBoard;
    }

    public void setCurrBoard(Board currBoard) {
        this.currBoard = currBoard;
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
