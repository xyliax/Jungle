package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Movable -> Piece -> Elephant
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Elephant extends Piece {
    /**
     * Constructor for Piece.
     *
     * @param row
     *         initial row number
     * @param col
     *         initial column number
     * @param player
     *         the player it belongs to
     */
    public Elephant(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 8;
        this.type = JungleType.ELEPHANT;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "EP";
            case CHINESE_SIMPLE, CHINESE_TRADITIONAL -> "象";
        };
    }
}
