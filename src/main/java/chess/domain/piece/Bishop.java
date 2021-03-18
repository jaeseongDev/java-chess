package chess.domain.piece;

import chess.domain.position.Direction;

public class Bishop extends Piece {
    private static final char NAME_WHEN_BLACK = 'B';
    private static final char NAME_WHEN_WHITE = 'b';
    private static final int LINE_COUNT = 8;

    public Bishop(final Boolean isBlack, final char x, final char y) {
        super(isBlack, x, y, Direction.diagonalDirection(), LINE_COUNT);
    }

    @Override
    char getName() {
        if (isBlack()) {
            return NAME_WHEN_BLACK;
        }
        return NAME_WHEN_WHITE;
    }
}