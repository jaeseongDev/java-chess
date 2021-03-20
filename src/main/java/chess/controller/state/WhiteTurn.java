package chess.controller.state;

import chess.domain.grid.Grid;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.view.OutputView;

public final class WhiteTurn extends Playing {
    @Override
    final GameState move(final Grid grid, final Piece sourcePiece, final Piece targetPiece) {
        validateSourcePieceIsEmpty(sourcePiece);
        validateIfWhite(sourcePiece);
        grid.move(sourcePiece, targetPiece);
        if (isKingCaught(targetPiece)) {
            OutputView.printWinner(Color.WHITE);
            return new End();
        }
        return new BlackTurn();
    }

    private void validateIfWhite(final Piece sourcePiece) {
        if (sourcePiece.color() == Color.BLACK) {
            throw new IllegalArgumentException("자신의 말만 옮길 수 있습니다.");
        }
    }
}
