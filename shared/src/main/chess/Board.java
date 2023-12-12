package chess;

public class Board implements ChessBoard {
    public ChessPiece[][] board;

    public Board() {
        board = new ChessPiece[8][8];
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    @Override
    public boolean isEmpty(Position position) {
        if (this.getPiece(position) == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void resetBoard() {
        clearBoard();
        setOriginPieces();
    }

    public ChessBoard deepCopyBoard() {
        // create a new board
        ChessBoard newBoard = new Board();
        // for every square of the old board
        for (int row=0;row<8;row++) {
            for (int column=0;column<8;column++) {
                ChessPiece currentPiece = this.getPiece(new Position(row, column));
                if (currentPiece != null) {
                    // make a deep copy of that piece and put in on the new board
                    ChessPiece newPiece = currentPiece.deepCopyPiece();
                    newBoard.addPiece(new Position(row, column), newPiece);
                }
            }
        }
        return newBoard;
    }

    private void setOriginPieces() {
        // set all pawns
        for (int column=0;column<board[1].length;column++) {
            addPiece(new Position(1, column), new PawnPiece(ChessGame.TeamColor.WHITE));
        }
        for (int column=0;column<board[6].length;column++) {
            addPiece(new Position(6, column), new PawnPiece(ChessGame.TeamColor.BLACK));
        }

        // set all rooks
        addPiece(new Position(0, 0), new RookPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(0, 7), new RookPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(7, 0), new RookPiece(ChessGame.TeamColor.BLACK));
        addPiece(new Position(7, 7), new RookPiece(ChessGame.TeamColor.BLACK));

        // set all knights
        addPiece(new Position(0, 1), new KnightPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(0, 6), new KnightPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(7, 1), new KnightPiece(ChessGame.TeamColor.BLACK));
        addPiece(new Position(7, 6), new KnightPiece(ChessGame.TeamColor.BLACK));

        // set all bishops
        addPiece(new Position(0, 2), new BishopPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(0, 5), new BishopPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(7, 2), new BishopPiece(ChessGame.TeamColor.BLACK));
        addPiece(new Position(7, 5), new BishopPiece(ChessGame.TeamColor.BLACK));

        // set all kings
        addPiece(new Position(0, 3), new KingPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(7, 3), new KingPiece(ChessGame.TeamColor.BLACK));

        // set all queens
        addPiece(new Position(0, 4), new QueenPiece(ChessGame.TeamColor.WHITE));
        addPiece(new Position(7, 4), new QueenPiece(ChessGame.TeamColor.BLACK));
    }

    public void clearBoard() {
        // set all squares to null
        for (int squareRow=0;squareRow<board.length;squareRow++) {
            for (int squareColumn=0;squareColumn<board[squareRow].length;squareColumn++) {
                board[squareRow][squareColumn] = null;
            }
        }
    }
}
