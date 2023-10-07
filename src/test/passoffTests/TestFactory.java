package passoffTests;

import chess.*;

/**
 * Used for testing your code
 * Add in code using your classes for each method for each FIXME
 */
public class TestFactory {

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard(){
		return new Board();
    }

    public static ChessGame getNewGame(){
		return new Game();
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        ChessPiece newPiece = null;
        if (type == ChessPiece.PieceType.PAWN) {
            newPiece = new PawnPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.ROOK) {
            newPiece = new RookPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.KNIGHT) {
            newPiece = new KnightPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.BISHOP) {
            newPiece = new BishopPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.KING) {
            newPiece = new KingPiece(pieceColor);
        } else if (type == ChessPiece.PieceType.QUEEN) {
            newPiece = new QueenPiece(pieceColor);
        }
		return newPiece;
    }

    public static ChessPosition getNewPosition(Integer row, Integer col){
        return new Position(row-1, col-1);
    }

    public static ChessMove getNewMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){
		return new Move(startPosition, endPosition, promotionPiece);
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort(){
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime(){
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 3000L;
    }
    //------------------------------------------------------------------------------------------------------------------
}
