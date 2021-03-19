package chess.service.state;

import chess.service.ChessService;
import chess.view.OutputView;

public class Status extends Finished {
    @Override
    public void playRound(ChessService chessService) {
        chessService.setGameOver(true);
        double blackScore = chessService.score(true);
        double whiteScore = chessService.score(false);
        OutputView.printScores(true, blackScore);
        OutputView.printScores(false, whiteScore);
        OutputView.printWinner(blackScore > whiteScore);
    }
}
