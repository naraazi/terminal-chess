package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public final class King extends ChessPiece {
    private static final int[][] OFFSETS = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
            {0, 1}, {1, -1}, {1, 0}, {1, 1}
    };

    private final ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = adjacentMoves();
        addCastlingMoves(moves);
        return moves;
    }

    @Override
    public boolean[][] attackMoves() {
        return adjacentMoves();
    }

    private boolean[][] adjacentMoves() {
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

    private void addCastlingMoves(boolean[][] moves) {
        if (getMoveCount() != 0 || chessMatch.isSquareUnderAttack(position, getColor())) {
            return;
        }

        addCastlingMove(moves, 3, new int[]{1, 2}, 2);
        addCastlingMove(moves, -4, new int[]{-1, -2, -3}, -2);
    }

    private void addCastlingMove(boolean[][] moves, int rookOffset, int[] clearOffsets, int kingOffset) {
        Position rookPosition = new Position(position.getRow(), position.getColumn() + rookOffset);
        if (!getBoard().positionExists(rookPosition) || !isUnmovedFriendlyRook(rookPosition)) {
            return;
        }

        for (int offset : clearOffsets) {
            Position square = new Position(position.getRow(), position.getColumn() + offset);
            if (getBoard().piece(square) != null) {
                return;
            }
        }

        Position transit = new Position(position.getRow(), position.getColumn() + Integer.signum(kingOffset));
        Position destination = new Position(position.getRow(), position.getColumn() + kingOffset);
        if (!chessMatch.isSquareUnderAttack(transit, getColor())
                && !chessMatch.isSquareUnderAttack(destination, getColor())) {
            moves[destination.getRow()][destination.getColumn()] = true;
        }
    }

    private boolean isUnmovedFriendlyRook(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece instanceof Rook
                && piece.getColor() == getColor()
                && piece.getMoveCount() == 0;
    }

    @Override
    public String toString() {
        return "♚";
    }
}
