package chess;

import chess.controller.ChessController;

public class ConsoleChessApplication {
    public static void main(String[] args) {
        ChessController controller = new ChessController();
        controller.run();
    }
}
