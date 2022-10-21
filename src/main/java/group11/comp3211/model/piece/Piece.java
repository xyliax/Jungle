package group11.comp3211.model.piece;

import group11.comp3211.common.exceptions.IllegalCaptureException;
import group11.comp3211.model.Movable;
import group11.comp3211.model.Player;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Piece implements Movable {
	protected int rank;
	protected int row;
	protected int col;
	protected Player player;

	public abstract void capture(Piece piece) throws IllegalCaptureException;
}
