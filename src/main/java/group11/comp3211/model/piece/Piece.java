package group11.comp3211.model.piece;

import group11.comp3211.model.Direction;
import group11.comp3211.model.JungleType;
import group11.comp3211.model.Movable;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Piece implements Movable, Serializable {
    protected int rank;
    protected int row;
    protected int col;
    protected boolean selected;
    protected Direction direction;
    protected Player player;
    protected JungleType type;

    public Piece(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.selected = false;
        this.direction = Direction.STAY;
        this.player = player;
        this.rank = -1;
        this.type = JungleType.UNDEFINED;
    }

    public abstract String getSymbol(Language language);

    @Override
    public JungleType type() {
        return type;
    }
}
