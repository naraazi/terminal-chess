package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public final class Bishop extends ChessPiece {
    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 1}, {1, 1}, {1, -1}
    };

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        return slidingMoves(DIRECTIONS);
    }

    @Override
    public String toString() {
        return "♝";
    }
}
