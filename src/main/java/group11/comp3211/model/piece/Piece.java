package group11.comp3211.model.piece;

import group11.comp3211.model.Direction;
import group11.comp3211.model.JungleType;
import group11.comp3211.model.Movable;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

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

    public void die() {
        row = col = -1;
    }

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return rank == piece.rank && row == piece.row && col == piece.col && selected == piece.selected
               && direction == piece.direction && player.equals(piece.player) && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, row, col, selected, direction, player, type);
    }
}
