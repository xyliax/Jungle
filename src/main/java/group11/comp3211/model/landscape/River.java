package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.IllegalMovementException;
import group11.comp3211.model.Movable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class River extends Landscape {
	@Override
	public void carry(Movable movable) throws IllegalMovementException {

	}
}
