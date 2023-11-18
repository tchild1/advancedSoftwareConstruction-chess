package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PawnPiece extends Piece {
    boolean moved;
    int moveTwoRow;
    public PawnPiece(ChessGame.TeamColor color) {
        super(ChessPiece.PieceType.PAWN, color);
        moved = false;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> allMoves = new HashSet<>();

        int moveTwoRow;
        // this defines which direction a piece should be moving
        int forward;
        int promotionRow;
        if (this.getTeamColor() == ChessGame.TeamColor.WHITE) {
            moveTwoRow = 1;
            forward = 1;
            promotionRow = 7;
        } else {
            moveTwoRow = 6;
            forward = -1;
            promotionRow = 0;
        }

        // for moving one or two squares forward
        for (int rowMove=1;rowMove<3;rowMove++) {
            // if only moving one or in move two row
            if (rowMove == 1 || myPosition.getRow() == moveTwoRow) {
                // create this move if it is a valid move
                Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, rowMove*forward, 0);
                // if the move was created and other team is not here
                if (thisMove != null && !this.isOtherTeamHere(board, new Position(myPosition.getRow()+rowMove*forward, myPosition.getColumn()))) {
                    if (thisMove.endPosition.getRow()==promotionRow) {
                        allMoves.addAll(this.promotePiece(thisMove));
                    } else {
                        allMoves.add(thisMove);
                    }
                } else {
                    break;
                }
            }
        }

        // for capturing
        Move thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 1*forward, 1);
        // if the move was created and other team is not here
        if (thisMove != null && this.isOtherTeamHere(board, new Position(myPosition.getRow()+1*forward, myPosition.getColumn()+1))) {
            if (thisMove.endPosition.getRow()==promotionRow) {
                allMoves.addAll(this.promotePiece(thisMove));
            } else {
                allMoves.add(thisMove);
            }
        }

        thisMove = this.isOnBoardAndMyTeamNotHere(board, myPosition, 1*forward, -1);
        // if the move was created and other team is not here
        if (thisMove!=null){
            if (this.isOtherTeamHere(board, new Position(myPosition.getRow()+1*forward, myPosition.getColumn()-1))) {
                if (thisMove.endPosition.getRow()==promotionRow) {
                    allMoves.addAll(this.promotePiece(thisMove));
                } else {
                    allMoves.add(thisMove);
                }
            }
        }

        return allMoves;
    }

    private Collection<ChessMove> promotePiece(Move move) {
        Set<ChessMove> allMoves = new HashSet<>();

        allMoves.add(new Move(move.startPosition, move.endPosition, PieceType.QUEEN));
        allMoves.add(new Move(move.startPosition, move.endPosition, PieceType.BISHOP));
        allMoves.add(new Move(move.startPosition, move.endPosition, PieceType.KNIGHT));
        allMoves.add(new Move(move.startPosition, move.endPosition, PieceType.ROOK));

        return allMoves;
    }
}
