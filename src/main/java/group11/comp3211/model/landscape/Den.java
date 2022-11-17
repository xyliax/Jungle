package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Loader -> Landscape -> Den
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Den extends Landscape {
    Player player;

    /**
     * Constructor for Den.
     * @param row initial row number
     * @param col initial column number
     * @param player the player it belongs to
     */
    public Den(int row, int col, Player player) {
        super(row, col);
        this.player = player;
        this.type = JungleType.DEN;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "DN";
            case CHINESE_SIMPLE, CHINESE_TRADITIONAL -> "穴";
        };
    }
}
