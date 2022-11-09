package group11.comp3211.model.landscape;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class River extends Landscape {
    public River(int row, int col) {
        super(row, col);
    }
}
