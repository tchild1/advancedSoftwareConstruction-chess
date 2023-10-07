package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KingPiece extends Piece {
    public KingPiece(ChessGame.TeamColor color) {
        super(PieceType.KING, color);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // initialize return collection
        Set<ChessMove> allMoves = new HashSet<>();

        // for every move in every direction that is not 0,0
        for (int rowMove=-1;rowMove<2;rowMove++) {
            for (int columnMove=-1;columnMove<2;columnMove++) {
                if (rowMove != 0 || columnMove != 0) {
                    // see if the move is valid, my team not here and is on board
                    Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, rowMove, columnMove);
                    // if the move was created, add it to the collection
                    if (thisMove != null) {
                        allMoves.add(thisMove);
                    }
                }
            }
        }
        return allMoves;
    }
}
