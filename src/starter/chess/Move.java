package chess;

import java.util.Objects;

public class Move implements ChessMove {

    ChessPosition startPosition;
    ChessPosition endPosition;
    ChessPiece.PieceType promotionPiece;
    boolean capture;

    public Move(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece) {
        startPosition = start;
        endPosition = end;
        this.promotionPiece = promotionPiece;
        capture = false;
    }

    @Override
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(startPosition, move.startPosition) && Objects.equals(endPosition, move.endPosition) && promotionPiece == move.promotionPiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotionPiece);
    }
}
