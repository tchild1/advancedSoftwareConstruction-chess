package chess;

import java.util.Collection;

public class Piece implements ChessPiece {

    PieceType ptype;
    ChessGame.TeamColor pcolor;

    public Piece(PieceType type, ChessGame.TeamColor color) {
        ptype = type;
        pcolor = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return pcolor;
    }

    @Override
    public PieceType getPieceType() {
        return this.ptype;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
