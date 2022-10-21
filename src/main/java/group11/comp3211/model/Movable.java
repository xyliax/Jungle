package group11.comp3211.model;

import group11.comp3211.common.exceptions.IllegalMovementException;

public interface Movable {
	void move(int dx, int dy) throws IllegalMovementException;
}
