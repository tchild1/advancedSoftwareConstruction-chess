package chess;

import java.util.Objects;

public class Position implements ChessPosition {

    private int row;
    private int column;

    public Position(int row, int column) {
        setRow(row);
        setColumn(column);
    }

    @Override
    public int getRow() {
        return row;
    }

    public void setRow(int newRow) {
        row = newRow;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public void setColumn(int newColumn) {
        column = newColumn;
    }

    public boolean isOnBoard() {
        return 0 <= this.getRow() && this.getRow() <= 7 && 0 <= this.getColumn() && this.getColumn() <= 7;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
