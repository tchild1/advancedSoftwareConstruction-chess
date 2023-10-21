package chess;

import java.util.Collection;
import java.util.Iterator;

public class Game implements ChessGame {
    ChessBoard board;
    TeamColor currentTurn;

    public Game() {
        // creates a board and sets it up with all pieces in starting positions
        board = new Board();
        board.resetBoard();
        currentTurn = TeamColor.WHITE;
    }
    @Override
    public TeamColor getTeamTurn() {
        return currentTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        this.currentTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // get the piece at this position
        ChessPiece thisPiece = this.board.getPiece(startPosition);

        // if there isn't a piece here return null
        if (thisPiece != null) {
            // get all possible moves for this piece
            Collection<ChessMove> allPossibleMoves = thisPiece.pieceMoves(this.board, startPosition);
            // for every possible move
            Iterator<ChessMove> iterator = allPossibleMoves.iterator();
            while (iterator.hasNext()) {
                ChessMove move = iterator.next();
                // copy the board
                ChessBoard newBoard = this.board.deepCopyBoard();
                // add the piece to its possible new position
                newBoard.addPiece(move.getEndPosition(), newBoard.getPiece(move.getStartPosition()));
                // remove the piece from its starting position
                newBoard.addPiece(move.getStartPosition(), null);
                // see if the team is in check
                if (isNewBoardInCheck(thisPiece.getTeamColor(), newBoard)) {
                    iterator.remove();
                }
            }
            return allPossibleMoves;
        } else {
            return null;
        }
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // get the piece at this position
        ChessPiece currPiece = this.board.getPiece(move.getStartPosition());
        if (currPiece.getTeamColor() == this.currentTurn) {
            // get all possible moves for the piece
            Collection<ChessMove> allMoves = currPiece.pieceMoves(this.board, move.getStartPosition());
            // see if this move is the same as any move possible, if not throw an error
            for (ChessMove possibleMove : allMoves) {
                if (possibleMove.getStartPosition().getRow() == move.getStartPosition().getRow() && possibleMove.getStartPosition().getColumn() == move.getStartPosition().getColumn() && possibleMove.getEndPosition().getRow() == move.getEndPosition().getRow() && move.getEndPosition().getColumn() == possibleMove.getEndPosition().getColumn()) {
                    // add the piece to its new position
                    this.board.addPiece(move.getEndPosition(), currPiece);
                    // remove the piece from its starting position
                    this.board.addPiece(move.getStartPosition(), null);
                    // check that this move doesn't put you in check, if it does, undo it
                    if (isInCheck(currPiece.getTeamColor())) {
                        this.board.addPiece(move.getStartPosition(), currPiece);
                        this.board.addPiece(move.getEndPosition(), null);
                        throw new InvalidMoveException();
                    }
                    // pawn promotion
                    if (move.getPromotionPiece() != null) {
                        currPiece.setPieceType(move.getPromotionPiece());
                    }

                    // change turn
                    if (this.currentTurn ==TeamColor.BLACK) {
                        this.currentTurn = TeamColor.WHITE;
                    } else {
                        this.currentTurn = TeamColor.BLACK;
                    }
                    return;
                }
            }
        }
        throw new InvalidMoveException();
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor opposingTeam;
        Position kingPosition = new Position(-1, -1);
        if (teamColor == TeamColor.BLACK) {
            opposingTeam = TeamColor.WHITE;
        } else {
            opposingTeam = TeamColor.BLACK;
        }

        // get kings position
        for (int row=0;row<8;row++) {
            for (int column=0;column<8;column++) {
                ChessPiece currentPiece = this.board.getPiece(new Position(row, column));
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new Position(row, column);
                }
            }
        }

        // for every square on the board
        for (int row=0;row<8;row++) {
            for (int column=0;column<8;column++) {
                // get the piece at that square
                ChessPiece currentPiece = this.board.getPiece(new Position(row, column));
                // if the piece is not empty and has an opposing piece there
                if (currentPiece != null && currentPiece.getTeamColor() == opposingTeam) {
                    // get all possible moves for that piece
                    Collection<ChessMove> allOpposingMoves = currentPiece.pieceMoves(this.board, new Position(row, column));
                    // for every possible move
                    for (ChessMove move : allOpposingMoves) {
                        // see if that move moves the piece to the kings square
                        if (move.getEndPosition().getRow() == kingPosition.getRow() && move.getEndPosition().getColumn() == kingPosition.getColumn()) {
                            return true;
                        }
                    }
                }
            }
        }

        // If no piece can touch the king, return false. the king is not in danger
        return false;
    }

    private boolean isNewBoardInCheck(TeamColor teamColor, ChessBoard board) {
        TeamColor opposingTeam;
        Position kingPosition = new Position(-1, -1);
        if (teamColor == TeamColor.BLACK) {
            opposingTeam = TeamColor.WHITE;
        } else {
            opposingTeam = TeamColor.BLACK;
        }

        // get kings position
        for (int row=0;row<8;row++) {
            for (int column=0;column<8;column++) {
                ChessPiece currentPiece = board.getPiece(new Position(row, column));
                if (currentPiece != null && currentPiece.getTeamColor() != opposingTeam && currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new Position(row, column);
                }
            }
        }

        // for every square on the board
        for (int row=0;row<8;row++) {
            for (int column=0;column<8;column++) {
                // get the piece at that square
                ChessPiece currentPiece = board.getPiece(new Position(row, column));
                // if the square is not empty and has the enemy has a piece there
                if (currentPiece != null && currentPiece.getTeamColor() == opposingTeam) {
                    // get all possible moves for that piece
                    Collection<ChessMove> allOpposingMoves = currentPiece.pieceMoves(board, new Position(row, column));
                    // for every possible move
                    for (ChessMove move : allOpposingMoves) {
                        // see if that move moves the piece to the kings square
                        if (move.getEndPosition().getRow() == kingPosition.getRow() && move.getEndPosition().getColumn() == kingPosition.getColumn()) {
                            return true;
                        }
                    }
                }
            }
        }

        // If no piece can touch the king, return false. the king is not in danger
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        // if the team is in check
        if (isInCheck(teamColor)) {
            // for every square on the real board
            for (int row=0;row<8;row++) {
                for (int column=0;column<8;column++) {
                    // get the piece in that square
                    ChessPiece currentPiece = this.board.getPiece(new Position(row, column));
                    // if the piece is not null and is of the team's color
                    if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                        // get all possible moves for that piece
                        Collection<ChessMove> allPossibleMoves = currentPiece.pieceMoves(this.board, new Position(row, column));
                        // for each possible move
                        for (ChessMove possibleMove : allPossibleMoves) {
                            // copy the board
                            ChessBoard newBoard = this.board.deepCopyBoard();
                            // add the piece to its possible new position
                            newBoard.addPiece(possibleMove.getEndPosition(), newBoard.getPiece(possibleMove.getStartPosition()));
                            // remove the piece from its starting position
                            newBoard.addPiece(possibleMove.getStartPosition(), null);
                            // see if the team is still in check
                            if (!isInCheck(teamColor)) {
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            return false;
        }

        // if no move can get the team out of check, they are in checkmate
        return true;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        // for every square on the board
        for (int row=0;row<8;row++) {
            for (int column=0;column<8;column++) {
                // get the piece at that square
                ChessPiece currentPiece = this.board.getPiece(new Position(row, column));
                // if the piece is not empty and the team has a piece there
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                    // get all possible moves for that piece
                    Collection<ChessMove> allMoves = currentPiece.pieceMoves(this.board, new Position(row, column));
                    // check that none of these moves put the team in check
                    Iterator<ChessMove> iterator = allMoves.iterator();
                    while (iterator.hasNext()) {
                        ChessMove possibleMove = iterator.next();
                        // copy the board
                        ChessBoard newBoard = this.board.deepCopyBoard();
                        // add the piece to its possible new position
                        newBoard.addPiece(possibleMove.getEndPosition(), newBoard.getPiece(possibleMove.getStartPosition()));
                        // remove the piece from its starting position
                        newBoard.addPiece(possibleMove.getStartPosition(), null);
                        // see if the team is still in check
                        if (isNewBoardInCheck(teamColor, newBoard)) {
                            iterator.remove();
                        }
                    }
                    // if there are any possible moves, the team is not in stalemate
                    if (allMoves.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board.clearBoard();
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return this.board;
    }
}
