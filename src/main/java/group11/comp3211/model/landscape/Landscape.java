package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.Loader;
import group11.comp3211.model.Movable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Landscape implements Loader {
    protected int row;
    protected int col;
    protected Movable load;
    protected Set<Movable> allowed;

    @Override
    public boolean canLoad(Movable movable) {
        return false;
    }

    @Override
    public void load(Movable movable) throws LogicException {

    }
}
