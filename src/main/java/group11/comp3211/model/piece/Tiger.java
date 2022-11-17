package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Movable -> Piece -> Tiger
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Tiger extends Piece {
    /**
     * Constructor for Tiger.
     * @param row initial row number
     * @param col initial column number
     * @param player the player it belongs to
     */
    public Tiger(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 6;
        this.type = JungleType.TIGER;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "TG";
            case CHINESE_SIMPLE, CHINESE_TRADITIONAL -> "虎";
        };
    }
}
