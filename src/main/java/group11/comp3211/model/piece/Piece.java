package group11.comp3211.model.piece;

import group11.comp3211.model.Direction;
import group11.comp3211.model.JungleType;
import group11.comp3211.model.Movable;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.Data;

import java.io.Serializable;

/**
 * Movable -> Piece
 */
@Data
public abstract class Piece implements Movable, Serializable {
    protected int rank;
    protected int row;
    protected int col;
    protected boolean selected;
    protected Direction direction;
    protected Player player;
    protected JungleType type;

    /**
     * Constructor for Piece.
     *
     * @param row
     *         initial row number
     * @param col
     *         initial column number
     * @param player
     *         the player it belongs to
     */
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

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
