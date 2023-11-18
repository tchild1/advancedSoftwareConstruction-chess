package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RookPiece extends Piece {
    public RookPiece(ChessGame.TeamColor color) {
        super(PieceType.ROOK, color);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // initialize collection to be returned
        Set<ChessMove> allMoves = new HashSet<>();

        // for moving every square forward as far as possible
        for (int rowMove=1;rowMove<8;rowMove++) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, rowMove, 0);
            // if the move was created, add it to the collection
            if (thisMove != null) {
                allMoves.add(thisMove);
            }
            // if this move was not created (not a valid move) or is a capture, stop moving in this direction
            if (thisMove == null || thisMove.capture) {
                break;
            }
        }

        // for moving every square backward as far as possible
        for (int rowMove=-1;rowMove>-8;rowMove = rowMove-1) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, rowMove, 0);
            // if the move was created, add it to the collection
            if (thisMove != null) {
                allMoves.add(thisMove);
            }
            // if this move was not created (not a valid move) or is a capture, stop moving in this direction
            if (thisMove == null || thisMove.capture) {
                break;
            }
        }

        // for moving every square left as far as possible
        for (int columnMove=-1;columnMove>-8;columnMove = columnMove - 1) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 0, columnMove);
            // if the move was created, add it to the collection
            if (thisMove != null) {
                allMoves.add(thisMove);
            }
            // if this move was not created (not a valid move) or is a capture, stop moving in this direction
            if (thisMove == null || thisMove.capture) {
                break;
            }
        }

        // for moving every square right as far as possible
        for (int columnMove=1;columnMove<8;columnMove++) {
            // create this move if it is a valid move
            Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 0, columnMove);
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
