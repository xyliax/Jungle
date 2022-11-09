package group11.comp3211.model.piece;

import group11.comp3211.model.Direction;
import group11.comp3211.model.Movable;
import group11.comp3211.model.Player;
import lombok.Data;

@Data
public abstract class Piece implements Movable {
    protected int rank;
    protected int row;
    protected int col;
    protected Direction direction;
    protected Player player;

    public Piece(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.direction = Direction.STAY;
        this.player = player;
    }
}
