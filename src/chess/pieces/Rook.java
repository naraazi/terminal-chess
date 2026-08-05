package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public final class Rook extends ChessPiece {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {0, -1}, {0, 1}, {1, 0}
    };

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        return slidingMoves(DIRECTIONS);
    }

    @Override
    public String toString() {
        return "♜";
    }
}
