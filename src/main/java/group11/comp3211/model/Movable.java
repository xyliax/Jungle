package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;

public interface Movable {
    void move(Direction direction) throws LogicException;
}
