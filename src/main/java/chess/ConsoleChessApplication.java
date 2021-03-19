package chess;

import chess.controller.ChessController;
import chess.service.ChessService;

public class ConsoleChessApplication {
    public static void main(String[] args) {
        ChessController controller = new ChessController(new ChessService());
        controller.run();
    }
}
