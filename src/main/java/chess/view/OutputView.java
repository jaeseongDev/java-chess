package chess.view;

import chess.domain.grid.Grid;

import java.util.stream.Collectors;

public class OutputView {
    public static void printStartChess() {
        System.out.println("체스 게임을 시작합니다");
        System.out.println("게임 시작 : start");
        System.out.println("게임 종료 : end");
        System.out.println("게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public static void printGridStatus(Grid grid) {
        String gridStatus = grid.getLines()
                .stream()
                .map(line -> line.getPieces()
                        .stream()
                        .map(piece -> Character.toString(piece.getName()))
                        .collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
        System.out.println(gridStatus);
    }
}
