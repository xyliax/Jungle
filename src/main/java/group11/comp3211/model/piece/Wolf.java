package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Movable -> Piece -> Wolf
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Wolf extends Piece {
    /**
     * Constructor for Wolf.
     *
     * @param row    initial row number
     * @param col    initial column number
     * @param player the player it belongs to
     */
    public Wolf(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 4;
        this.type = JungleType.WOLF;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "WF";
            case CHINESE_SIMPLE, CHINESE_TRADITIONAL -> "狼";
        };
    }
}
