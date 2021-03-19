package chess.domain.piece;

import chess.domain.board.Direction;
import chess.domain.dto.Strategy;
import chess.domain.board.Team;

public class Knight extends AbstractPiece {
    public static final double POINT = 2.5;
    private static final int MOVE_RANGE = 2;

    public Knight(Team team) {
        super(team);
    }

    @Override
    public Strategy strategy() {
        return new Strategy(Direction.knightDirection(), MOVE_RANGE);
    }

    @Override
    public String getName() {
        if (isBlackTeam()) {
            return "N";
        }
        return "n";
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public double getPoint() {
        return POINT;
    }
}
