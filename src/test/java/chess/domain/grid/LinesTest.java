package chess.domain.grid;

import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Rook;
import chess.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LinesTest {
    Lines lines;

    @BeforeEach
    public void setup() {
        List<Line> linesGroup = Arrays.asList(
                Line.empty(8),
                Line.empty(7),
                Line.empty(6),
                Line.empty(5),
                Line.general(4, Color.WHITE),
                Line.empty(3),
                Line.empty(2),
                Line.empty(1)
        );
        lines = new Lines(linesGroup);
    }

    @Test
    @DisplayName("Lines에서 해당 위치에 해당하는 Piece 제대로 가져오는 지 테스트")
    public void piece() {
        Position position = new Position("a4");
        Piece piece = lines.piece(position); // 룩(Rook)
        assertThat(piece.getClass()).isEqualTo(Rook.class);
        assertThat(piece.position()).isEqualTo(position);
    }

    @Test
    @DisplayName("Lines에서 해당 위치에 Piece를 할당하는 지 테스트")
    public void assign() {
        Piece piece = new King(Color.WHITE, 'a', '2');
        Position position = new Position("a2");
        lines.assign(position, new King(Color.WHITE, 'a', '2'));
        assertThat(lines.piece(position)).isEqualTo(piece);
    }

    @ParameterizedTest
    @DisplayName("Empty인지 제대로 테스트하는 지 테스트")
    @CsvSource(value = {"a1:true", "a4:false"}, delimiter = ':')
    public void isEmpty(String position, boolean result) {
        assertThat(lines.isEmpty(new Position(position))).isEqualTo(result);
    }
}