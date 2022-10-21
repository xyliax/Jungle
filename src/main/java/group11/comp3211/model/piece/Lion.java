package group11.comp3211.model.piece;

import group11.comp3211.common.exceptions.IllegalCaptureException;
import group11.comp3211.common.exceptions.IllegalMovementException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class Lion extends Piece {
    public Lion() {
        rank = 7;
    }

    @Override
    public void move(int dx, int dy) throws IllegalMovementException {

    }

    @Override
    public void capture(Piece piece) throws IllegalCaptureException {

    }
}
