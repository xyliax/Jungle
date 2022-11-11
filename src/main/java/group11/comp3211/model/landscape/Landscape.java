package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.Loader;
import group11.comp3211.model.Movable;
import group11.comp3211.view.Language;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public abstract class Landscape implements Loader, Serializable {
    protected int row;
    protected int col;
    protected Movable load;
    protected Set<Movable> allowed;

    public Landscape(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String getSymbol(Language language) {
        return null;
    }

    @Override
    public boolean canLoad(Movable movable) {
        return false;
    }


    @Override
    public void load(Movable movable) throws LogicException {
        this.load = movable;
    }
}
