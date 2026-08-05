package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
    private final Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    protected void increaseMoveCount() {
        moveCount++;
    }

    protected void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    public boolean[][] attackMoves() {
        return possibleMoves();
    }

    protected boolean canMoveTo(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || piece.getColor() != color;
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);

        return piece != null && piece.getColor() != color;
    }

    protected boolean[][] slidingMoves(int[][] directions) {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        for (int[] direction : directions) {
            Position candidate = new Position(
                    position.getRow() + direction[0],
                    position.getColumn() + direction[1]
            );

            while (getBoard().positionExists(candidate) && !getBoard().thereIsAPiece(candidate)) {
                moves[candidate.getRow()][candidate.getColumn()] = true;
                candidate.setValues(
                        candidate.getRow() + direction[0],
                        candidate.getColumn() + direction[1]
                );
            }

            if (getBoard().positionExists(candidate) && isThereOpponentPiece(candidate)) {
                moves[candidate.getRow()][candidate.getColumn()] = true;
            }
        }

        return moves;
    }
}
