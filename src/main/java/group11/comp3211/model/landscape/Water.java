package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Loader -> Landscape -> Water
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Water extends Landscape {
    JungleType area;

    /**
     * Constructor for Water.
     *
     * @param row
     *         initial row number
     * @param col
     *         initial column number
     */
    public Water(int row, int col) {
        super(row, col);
        this.type = JungleType.WATER;
        this.allowed.remove(JungleType.ALL);
        this.allowed.add(JungleType.RAT);
        this.area = col < 4 ? JungleType.RIVER_AREA_LEFT : JungleType.RIVER_AREA_RIGHT;
    }
}
