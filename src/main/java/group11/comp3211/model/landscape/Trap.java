package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Loader -> Landscape -> Trap
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Trap extends Landscape {
    Player player;

    /**
     * Constructor for Trap.
     * @param row initial row number
     * @param col initial column number
     * @param player the player it belongs to
     */
    public Trap(int row, int col, Player player) {
        super(row, col);
        this.player = player;
        this.type = JungleType.TRAP;
    }
}
