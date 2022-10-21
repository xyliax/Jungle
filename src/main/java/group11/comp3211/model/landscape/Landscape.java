package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.IllegalMovementException;
import group11.comp3211.model.Movable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Landscape {
	protected int row;
	protected int col;
	protected Movable movable;
	protected Set<Movable> allowed;

	public abstract void carry(Movable movable) throws IllegalMovementException;
}
