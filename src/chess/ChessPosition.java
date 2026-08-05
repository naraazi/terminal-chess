package chess;

import boardgame.Position;

public record ChessPosition(char column, int row) {
    public ChessPosition {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
        }

    }

    Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString() {
        return "%c%d".formatted(column, row);
    }
}
