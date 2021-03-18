package chess.domain.piece;

import chess.domain.position.Direction;

public class Rook extends Piece {
    private static final char NAME_WHEN_BLACK = 'R';
    private static final char NAME_WHEN_WHITE = 'r';
    private static final int LINE_COUNT = 8;

    public Rook(final Boolean isBlack, final char x, final char y) {
        super(isBlack, x, y, Direction.linearDirection(), LINE_COUNT);
    }

    @Override
    char getName() {
        if (isBlack()) {
            return NAME_WHEN_BLACK;
        }
        return NAME_WHEN_WHITE;
    }
}