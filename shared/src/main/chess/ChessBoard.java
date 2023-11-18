package chess;

/**
 * A chessboard that can hold and rearrange chess pieces
 */
public interface ChessBoard {

    /**
     * Adds a chess piece to the chessboard
     * @param position where to add the piece to
     * @param piece the piece to add
     */
    void addPiece(ChessPosition position, ChessPiece piece);

    /**
     * Gets a chess piece on the chessboard
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that position
     */
    ChessPiece getPiece(ChessPosition position);


    /**
     * returns true if a position is empty
     * @param position The position to check
     * @return True or False
     */
    public boolean isEmpty(Position position);

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    void resetBoard();

    /**
     * Clears all pieces off board
     */
    void clearBoard();

    ChessBoard deepCopyBoard();
}
