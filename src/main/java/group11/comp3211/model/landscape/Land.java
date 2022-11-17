package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Loader -> Landscape -> Land
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Land extends Landscape {
    /**
     * Constructor for Land.
     * @param row initial row number
     * @param col initial column number
     */
    public Land(int row, int col) {
        super(row, col);
        this.type = JungleType.LAND;
    }
}
