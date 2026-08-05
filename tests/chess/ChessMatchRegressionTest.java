package chess;

import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;

public final class ChessMatchRegressionTest {
    private ChessMatchRegressionTest() {
    }

    public static void main(String[] args) {
        shouldStartWithTheStandardPosition();
        shouldDetectFoolsMate();
        shouldPerformWhiteEnPassant();
        shouldPerformBlackEnPassant();
        shouldCastleKingside();
        shouldPromoteToTheSelectedPiece();
        System.out.println("All chess regression tests passed.");
    }

    private static void shouldStartWithTheStandardPosition() {
        ChessMatch match = new ChessMatch();

        require(match.getTurn() == 1, "The match should start on turn one");
        require(match.getCurrentPlayer() == Color.WHITE, "White should move first");
        require(countPieces(match) == 32, "The initial board should contain 32 pieces");
        require(!match.isCheck(), "The initial position should not be check");
        require(!match.isCheckmate(), "The initial position should not be checkmate");
    }

    private static void shouldDetectFoolsMate() {
        ChessMatch match = new ChessMatch();

        move(match, "f2", "f3");
        move(match, "e7", "e5");
        move(match, "g2", "g4");
        move(match, "d8", "h4");

        require(match.isCheck(), "Fool's mate should put White in check");
        require(match.isCheckmate(), "Fool's mate should end in checkmate");
        require(match.getCurrentPlayer() == Color.BLACK, "Black should be reported as the winner");
    }

    private static void shouldPerformWhiteEnPassant() {
        ChessMatch match = new ChessMatch();

        move(match, "e2", "e4");
        move(match, "a7", "a6");
        move(match, "e4", "e5");
        move(match, "d7", "d5");
        ChessPiece captured = move(match, "e5", "d6");

        require(captured instanceof Pawn && captured.getColor() == Color.BLACK,
                "White should capture the vulnerable black pawn");
        require(pieceAt(match, "d6") instanceof Pawn, "The white pawn should finish on d6");
        require(pieceAt(match, "d5") == null, "The captured pawn should be removed from d5");
    }

    private static void shouldPerformBlackEnPassant() {
        ChessMatch match = new ChessMatch();

        move(match, "a2", "a3");
        move(match, "e7", "e5");
        move(match, "a3", "a4");
        move(match, "e5", "e4");
        move(match, "d2", "d4");
        ChessPiece captured = move(match, "e4", "d3");

        require(captured instanceof Pawn && captured.getColor() == Color.WHITE,
                "Black should capture the vulnerable white pawn");
        require(pieceAt(match, "d3") instanceof Pawn, "The black pawn should finish on d3");
        require(pieceAt(match, "d4") == null, "The captured pawn should be removed from d4");
    }

    private static void shouldCastleKingside() {
        ChessMatch match = new ChessMatch();

        move(match, "e2", "e4");
        move(match, "a7", "a6");
        move(match, "g1", "f3");
        move(match, "a6", "a5");
        move(match, "f1", "e2");
        move(match, "b7", "b6");
        move(match, "e1", "g1");

        require(pieceAt(match, "g1") instanceof King, "The king should finish on g1");
        require(pieceAt(match, "f1") instanceof chess.pieces.Rook, "The rook should finish on f1");
    }

    private static void shouldPromoteToTheSelectedPiece() {
        ChessMatch match = new ChessMatch();

        move(match, "a2", "a4");
        move(match, "b7", "b5");
        move(match, "a4", "b5");
        move(match, "a7", "a6");
        move(match, "b5", "a6");
        move(match, "h7", "h6");
        move(match, "a6", "a7");
        move(match, "h6", "h5");
        move(match, "a7", "b8");
        match.replacePromotedPiece("N");

        require(pieceAt(match, "b8") instanceof Knight,
                "The pawn should be replaced with the selected promotion piece");
    }

    private static ChessPiece move(ChessMatch match, String source, String target) {
        return match.performChessMove(position(source), position(target));
    }

    private static ChessPiece pieceAt(ChessMatch match, String square) {
        ChessPosition position = position(square);
        return match.getPieces()[8 - position.row()][position.column() - 'a'];
    }

    private static int countPieces(ChessMatch match) {
        int count = 0;
        for (ChessPiece[] row : match.getPieces()) {
            for (ChessPiece piece : row) {
                if (piece != null) {
                    count++;
                }
            }
        }
        return count;
    }

    private static ChessPosition position(String square) {
        return new ChessPosition(square.charAt(0), Integer.parseInt(square.substring(1)));
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
