package group11.comp3211.model.piece;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.Direction;
import group11.comp3211.model.Player;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Leopard extends Piece {
    public Leopard(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 5;
    }

    @Override
    public void move(Direction direction) throws LogicException {

    }
}
