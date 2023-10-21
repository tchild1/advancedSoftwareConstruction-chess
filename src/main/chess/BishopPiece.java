package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BishopPiece extends Piece {
    public BishopPiece(ChessGame.TeamColor color) {
        super(PieceType.BISHOP, color);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> allMoves = new HashSet<>();

        // for moving every square forward and to the right as far as possible
        for (int move=1;move<8;move++) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, move, move);
            // if the move was created, add it to the collection
            if (thisMove != null) {
                allMoves.add(thisMove);
            }
            // if this move was not created (not a valid move) or is a capture, stop moving in this direction
            if (thisMove == null || thisMove.capture) {
                break;
            }
        }

        // for moving every square forward and to the left as far as possible
        for (int move=1;move<8;move++) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, move, move*-1);
            // if the move was created, add it to the collection
            if (thisMove != null) {
                allMoves.add(thisMove);
            }
            // if this move was not created (not a valid move) or is a capture, stop moving in this direction
            if (thisMove == null || thisMove.capture) {
                break;
            }
        }

        // for moving every square forward and to the right as far as possible
        for (int move=1;move<8;move++) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, move*-1, move*-1);
            // if the move was created, add it to the collection
            if (thisMove != null) {
                allMoves.add(thisMove);
            }
            // if this move was not created (not a valid move) or is a capture, stop moving in this direction
            if (thisMove == null || thisMove.capture) {
                break;
            }
        }

        // for moving every square forward and to the right as far as possible
        for (int move=1;move<8;move++) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, move*-1, move);
            // if the move was created, add it to the collection
            if (thisMove != null) {
                allMoves.add(thisMove);
            }
            // if this move was not created (not a valid move) or is a capture, stop moving in this direction
            if (thisMove == null || thisMove.capture) {
                break;
            }
        }

        return allMoves;
    }
}
