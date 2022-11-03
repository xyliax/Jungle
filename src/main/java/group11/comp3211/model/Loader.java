package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;

public interface Loader {
    boolean canLoad(Movable movable);

    void load(Movable movable) throws LogicException;
}
