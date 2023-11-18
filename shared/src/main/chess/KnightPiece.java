package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KnightPiece extends Piece {
    public KnightPiece(ChessGame.TeamColor color) {
        super(PieceType.KNIGHT, color);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // initialize return collection
        Set<ChessMove> allMoves = new HashSet<>();

        // create this move if it is a valid move
        Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 2, -1);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        // create this move if it is a valid move
        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 2, 1);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        // create this move if it is a valid move
        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, -2, 1);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        // create this move if it is a valid move
        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, -2, -1);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        // create this move if it is a valid move
        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 1, -2);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        // create this move if it is a valid move
        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 1, 2);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        // create this move if it is a valid move
        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, -1, -2);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        // create this move if it is a valid move
        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, -1, 2);
        // if the move was created, add it to the collection
        if (thisMove != null) {
            allMoves.add(thisMove);
        }

        return allMoves;
    }
}
