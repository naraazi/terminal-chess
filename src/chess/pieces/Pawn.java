package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public final class Pawn extends ChessPiece {
    private final ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        int direction = getColor() == Color.WHITE ? -1 : 1;

        Position oneStep = new Position(position.getRow() + direction, position.getColumn());
        if (getBoard().positionExists(oneStep) && !getBoard().thereIsAPiece(oneStep)) {
            moves[oneStep.getRow()][oneStep.getColumn()] = true;

            Position twoSteps = new Position(position.getRow() + 2 * direction, position.getColumn());
            if (getMoveCount() == 0
                    && getBoard().positionExists(twoSteps)
                    && !getBoard().thereIsAPiece(twoSteps)) {
                moves[twoSteps.getRow()][twoSteps.getColumn()] = true;
            }
        }

        addRegularCaptures(moves, direction);
        addEnPassantMoves(moves, direction);
        return moves;
    }

    @Override
    public boolean[][] attackMoves() {
        boolean[][] attacks = new boolean[getBoard().getRows()][getBoard().getColumns()];
        int direction = getColor() == Color.WHITE ? -1 : 1;

        for (int columnOffset : new int[]{-1, 1}) {
            Position target = new Position(
                    position.getRow() + direction,
                    position.getColumn() + columnOffset
            );
            if (getBoard().positionExists(target)) {
                attacks[target.getRow()][target.getColumn()] = true;
            }
        }

        return attacks;
    }

    private void addRegularCaptures(boolean[][] moves, int direction) {
        for (int columnOffset : new int[]{-1, 1}) {
            Position target = new Position(
                    position.getRow() + direction,
                    position.getColumn() + columnOffset
            );
            if (getBoard().positionExists(target) && isThereOpponentPiece(target)) {
                moves[target.getRow()][target.getColumn()] = true;
            }
        }
    }

    private void addEnPassantMoves(boolean[][] moves, int direction) {
        int enPassantRow = getColor() == Color.WHITE ? 3 : 4;
        if (position.getRow() != enPassantRow) {
            return;
        }

        for (int columnOffset : new int[]{-1, 1}) {
            Position adjacent = new Position(
                    position.getRow(),
                    position.getColumn() + columnOffset
            );

            if (getBoard().positionExists(adjacent)
                    && getBoard().piece(adjacent) == chessMatch.getEnPassantVulnerable()) {
                moves[position.getRow() + direction][adjacent.getColumn()] = true;
            }
        }
    }

    @Override
    public String toString() {
        return "♟";
    }
}
