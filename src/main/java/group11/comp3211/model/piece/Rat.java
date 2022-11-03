package group11.comp3211.model.piece;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.Direction;
import group11.comp3211.model.Player;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public final class Rat extends Piece {
    public Rat(int row, int col, Player player) {
        super(row, col, player);
        rank = 1;
    }

    @Override
    public void move(Direction direction) throws LogicException {

    }
}
