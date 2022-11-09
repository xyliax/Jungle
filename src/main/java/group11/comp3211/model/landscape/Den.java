package group11.comp3211.model.landscape;

import group11.comp3211.model.Player;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Den extends Landscape {
    Player player;

    public Den(int row, int col, Player player) {
        super(row, col);
        this.player = player;
    }
}
