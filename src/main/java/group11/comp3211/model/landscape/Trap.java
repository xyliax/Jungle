package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Trap extends Landscape {
    Player player;

    public Trap(int row, int col, Player player) {
        super(row, col);
        this.player = player;
        this.type = JungleType.TRAP;
    }
}
