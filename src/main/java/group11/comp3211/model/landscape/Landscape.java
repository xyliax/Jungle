package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Loader;
import group11.comp3211.model.Movable;
import group11.comp3211.view.Language;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Loader -> Landscape
 */
@Data
public abstract class Landscape implements Loader, Serializable {
    protected int row;
    protected int col;
    protected Movable load;
    protected Set<JungleType> allowed;
    protected JungleType type;

    /**
     * Constructor for Landscape
     *
     * @param row
     *         the initial row number
     * @param col
     *         the initial column number
     */
    public Landscape(int row, int col) {
        this.row = row;
        this.col = col;
        this.load = null;
        this.allowed = new HashSet<>();
        this.allowed.add(JungleType.ALL);
        this.type = JungleType.UNDEFINED;
    }

    @Override
    public boolean canLoad(Movable movable) {
        return allowed.contains(JungleType.ALL) || allowed.contains(movable.getType());
    }

    @Override
    public String getSymbol(Language language) {
        return null;
    }
}
