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
    public void setPieceType(PieceType type) {
       this.ptype = type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }

    public boolean occupiedByMyTeam(ChessBoard board, ChessPosition position) {
        if (board.getPiece(position) == null) {
            return false;
        }
        return this.getTeamColor() == board.getPiece(position).getTeamColor();
    }

    public boolean canMoveToReplaceThisFunctionWithBelow(ChessBoard board, Position position) {
        if (board.getPiece(position) == null) {
            return true;
        } else return board.getPiece(position).getTeamColor() != this.getTeamColor();
    }

    public boolean isMyTeamHere(ChessBoard board, Position position) {
        if (board.getPiece(position)==null) {
            return false;
        }
        if (board.getPiece(position).getTeamColor() == this.getTeamColor()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOtherTeamHere(ChessBoard board, Position position) {
        if (board.getPiece(position)==null) {
            return false;
        }
        if (board.getPiece(position).getTeamColor() != this.getTeamColor()) {
            return true;
        } else {
            return false;
        }
    }

    public Move isOnBoardAndMyTeamNotHere(ChessBoard board, ChessPosition myPosition, int rowMove, int columnMove) {
        Position newPosition = new Position(myPosition.getRow() + rowMove, myPosition.getColumn() + columnMove);
        // move is valid if:
        // the position is on the board
        if (newPosition.isOnBoard()) {
            // and my team is not here
            if (!this.isMyTeamHere(board, newPosition)) {
                // create this move
                Move thisMove = new Move(myPosition, newPosition, null);
                // if this move is a capture, flag it, so I stop moving in that direction
                if (this.isOtherTeamHere(board, newPosition)) {
                    thisMove.capture = true;
                }
                return thisMove;
            }
        }
        return null;
    }

    @Override
    public ChessPiece deepCopyPiece() {
        ChessPiece newPiece = null;
        if (this.getPieceType() == ChessPiece.PieceType.PAWN) {
            newPiece = new PawnPiece(this.getTeamColor());
        } else if (this.getPieceType() == ChessPiece.PieceType.ROOK) {
            newPiece = new RookPiece(this.getTeamColor());
        } else if (this.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            newPiece = new KnightPiece(this.getTeamColor());
        } else if (this.getPieceType() == ChessPiece.PieceType.BISHOP) {
            newPiece = new BishopPiece(this.getTeamColor());
        } else if (this.getPieceType() == ChessPiece.PieceType.KING) {
            newPiece = new KingPiece(this.getTeamColor());
        } else if (this.getPieceType() == ChessPiece.PieceType.QUEEN) {
            newPiece = new QueenPiece(this.getTeamColor());
        }
        return newPiece;
    }
}
