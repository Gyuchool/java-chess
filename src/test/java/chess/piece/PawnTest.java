package chess.piece;

import chess.Position;
import chess.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PawnTest {

    @Test
    @DisplayName("폰은 처음 상태에서 1칸 또는 2칸 전진할 수 있으면 true 반환")
    void isCorrectMovable() {
        Pawn pawn = new Pawn(Position.of('a', '2'), Team.WHITE);

        assertAll(
                () -> assertThat(pawn.isMovable(Position.of('a', '3'))).isTrue(),
                () -> assertThat(pawn.isMovable(Position.of('a', '4'))).isTrue()
        );
    }

    @Test
    @DisplayName("폰은 처음 상태에서 3칸 전진하려면 false 반환")
    void isNotCorrectInitMovable() {
        Pawn pawn = new Pawn(Position.of('a', '2'), Team.WHITE);

        assertThat(pawn.isMovable(Position.of('a', '5'))).isFalse();
    }

    @Test
    @DisplayName("폰은 한번 이동한 후, 2칸 움직이면 false 반환")
    void isNotCorrectMovable() {
        Pawn pawn = new Pawn(Position.of('a', '3'), Team.WHITE);

        assertThat(pawn.isMovable(Position.of('a', '5'))).isFalse();
    }

    @Test
    @DisplayName("폰이 뒤로 움직이면 false 반환")
    void isNotCorrectBackMovable() {
        Pawn pawn = new Pawn(Position.of('a', '3'), Team.WHITE);

        assertThat(pawn.isMovable(Position.of('a', '2'))).isFalse();
    }

    @Test
    @DisplayName("폰이 블랙팀이면 Rank가 감소하는 방향으로 알맞게 움직이면 true 반환")
    void isCorrectMovableWhenTeamIsBlack() {
        Pawn pawn = new Pawn(Position.of('a', '7'), Team.BLACK);

        assertThat(pawn.isMovable(Position.of('a', '6'))).isTrue();
    }

}
