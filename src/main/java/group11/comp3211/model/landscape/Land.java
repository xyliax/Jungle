package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Land extends Landscape {
    public Land(int row, int col) {
        super(row, col);
        this.type = JungleType.LAND;
    }
}
