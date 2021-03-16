package chess.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.*;

public class PositionTest {
    @Test
    @DisplayName("포지션 객체가 정상적으로 생성됐는 지 예외를 안 던지는 지 테스트")
    public void init() {
        assertThatCode(() -> {
            new Position('a', '1');
            new Position("a1");
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("체스 말의 위치가 Grid 범위 내에 있을 때에는 정상적으로 생성하는 지 테스트")
    @CsvSource(value = {"a:1", "a:2", "g:3", "h:7", "h:8"}, delimiter = ':')
    public void validatePosition(char horizontal, char vertical) {
        assertThatCode(() -> {
            new Position(horizontal, vertical);
        }).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("체스 말의 위치가 Grid 범위를 벗어나면 예외 출력")
    @CsvSource(value = {"a:9", "i:2"}, delimiter = ':')
    public void validatePosition_ThrowsException(char horizontal, char vertical) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Position(horizontal, vertical);
        }).withMessage("체스 말의 위치가 Grid 범위를 벗어났습니다.");
    }

    @ParameterizedTest(name = "{displayName}")
    @DisplayName("Null 값 입력시 예외 출력")
    @NullSource
    public void validateNullThrowsException(char horizontal, char vertical) {
        assertThatThrownBy(() -> new Position(horizontal, vertical)).isInstanceOf(NullPointerException.class)
                .hasMessage("Null값은 허용하지 않습니다.");
    }
}
