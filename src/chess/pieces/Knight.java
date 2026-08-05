package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public final class Knight extends ChessPiece {
    private static final int[][] OFFSETS = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        for (int[] offset : OFFSETS) {
            Position candidate = new Position(
                    position.getRow() + offset[0],
                    position.getColumn() + offset[1]
            );
            if (getBoard().positionExists(candidate) && canMoveTo(candidate)) {
                moves[candidate.getRow()][candidate.getColumn()] = true;
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return "♞";
    }
}
