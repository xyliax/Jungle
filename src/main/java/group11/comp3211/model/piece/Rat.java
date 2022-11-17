package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Movable -> Piece -> Rat
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Rat extends Piece {
    /**
     * Constructor for Rat.
     * @param row initial row number
     * @param col initial column number
     * @param player the player it belongs to
     */
    public Rat(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 1;
        this.type = JungleType.RAT;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "MS";
            case CHINESE_SIMPLE, CHINESE_TRADITIONAL -> "鼠";
        };
    }
}
