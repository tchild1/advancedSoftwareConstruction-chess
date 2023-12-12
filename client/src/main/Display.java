import chess.Board;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

public class Display {

    public static void printChessBoard(ChessGame.TeamColor color, Board board) {
        if (color == ChessGame.TeamColor.WHITE || color == ChessGame.TeamColor.OBSERVER) {
            printChessboardForWhite(board);
        } else {
            printChessboardForBlack(board);
        }
    }

    public static void printChessboardForWhite(Board b) {
        int numRow = 1;
        System.out.print("\n");
        printBorderBackwards();
        for (int row=b.board.length-1;row>=0;row--) {
            System.out.print("\u001b[30;100m " + numRow + " \u001b[0m");
            for (int col=b.board.length-1;col>=0;col--) {
                if (((row + col) % 2) == 0) {
                    System.out.printf("\u001b[107m%s\u001b[0m", getPieceRepresentation(b.board[row][col]));
                } else {
                    System.out.printf("\u001b[40m%s\u001b[0m", getPieceRepresentation(b.board[row][col]));
                }
            }
            System.out.print("\u001b[30;100m " + numRow + " \u001b[0m");
            System.out.print("\n");
            numRow++;
        }
        printBorderBackwards();
        System.out.print("\n");
    }

    public static void printChessboardForBlack(Board b) {
        int numRow = 8;
        System.out.print("\n");
        printBorderInOrder();
        for (int row=0;row<b.board.length;row++) {
            System.out.print("\u001b[30;100m " + numRow + " \u001b[0m");
            for (int col=0;col<b.board[row].length;col++) {
                if (((row + col) % 2) == 0) {
                    System.out.printf("\u001b[107m%s\u001b[0m", getPieceRepresentation(b.board[row][col]));
                } else {
                    System.out.printf("\u001b[40m%s\u001b[0m", getPieceRepresentation(b.board[row][col]));
                }
            }
            System.out.print("\u001b[30;100m " + numRow + " \u001b[0m");
            System.out.print("\n");
            numRow--;
        }
        printBorderInOrder();
        System.out.print("\n");
    }

    private static void printBorderInOrder() {
        System.out.print("\u001b[30;100m   \u001b[0m");
        System.out.print("\u001b[30;100m a \u001b[0m");
        System.out.print("\u001b[30;100m b \u001b[0m");
        System.out.print("\u001b[30;100m c \u001b[0m");
        System.out.print("\u001b[30;100m d \u001b[0m");
        System.out.print("\u001b[30;100m e \u001b[0m");
        System.out.print("\u001b[30;100m f \u001b[0m");
        System.out.print("\u001b[30;100m g \u001b[0m");
        System.out.print("\u001b[30;100m h \u001b[0m");
        System.out.print("\u001b[30;100m   \u001b[0m");
        System.out.print("\n");
    }

    private static void printBorderBackwards() {
        System.out.print("\u001b[30;100m   \u001b[0m");
        System.out.print("\u001b[30;100m h \u001b[0m");
        System.out.print("\u001b[30;100m g \u001b[0m");
        System.out.print("\u001b[30;100m f \u001b[0m");
        System.out.print("\u001b[30;100m e \u001b[0m");
        System.out.print("\u001b[30;100m d \u001b[0m");
        System.out.print("\u001b[30;100m c \u001b[0m");
        System.out.print("\u001b[30;100m b \u001b[0m");
        System.out.print("\u001b[30;100m a \u001b[0m");
        System.out.print("\u001b[30;100m   \u001b[0m");
        System.out.print("\n");
    }

    private static String getPieceRepresentation(ChessPiece piece) {
        if (piece == null) {
            return "\u001b[34m   \u001b[0m";
        }

        String pieceRepresentation1;
        String pieceRepresentation3;
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            pieceRepresentation1 = "\u001b[34m ";
        } else {
            pieceRepresentation1 = "\u001b[31m ";
        }
        pieceRepresentation3 = " \u001b[0m";


        String pieceRepresentation2;
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            pieceRepresentation2 = "K";
        } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            pieceRepresentation2 = "Q";
        } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            pieceRepresentation2 = "P";
        } else if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            pieceRepresentation2 = "R";
        } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            pieceRepresentation2 = "N";
        } else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            pieceRepresentation2 = "B";
        } else {
            pieceRepresentation2 = " ";
        }

        return pieceRepresentation1 + pieceRepresentation2 + pieceRepresentation3;
    }
}
