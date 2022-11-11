package group11.comp3211.model.piece;

import group11.comp3211.model.Direction;
import group11.comp3211.model.Movable;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.Data;

@Data
public abstract class Piece implements Movable {
    protected int rank;
    protected int row;
    protected int col;
    protected boolean selected;
    protected Direction direction;
    protected Player player;

    public Piece(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.selected = false;
        this.direction = Direction.STAY;
        this.player = player;
    }

    public abstract String getSymbol(Language language);
}
