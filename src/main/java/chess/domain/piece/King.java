package chess.domain.piece;

import chess.domain.position.Direction;

public class King extends Piece {
    private static final char NAME_WHEN_BLACK = 'K';
    private static final char NAME_WHEN_WHITE = 'k';

    public King(final Boolean isBlack, final char x, final char y) {
        super(isBlack, x, y, Direction.everyDirection(), 1);
    }

    @Override
    char getName() {
        if (isBlack()) {
            return NAME_WHEN_BLACK;
        }
        return NAME_WHEN_WHITE;
    }
}
