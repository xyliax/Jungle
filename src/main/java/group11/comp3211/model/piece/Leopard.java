package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Movable -> Piece -> Leopard
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Leopard extends Piece {
    /**
     * Constructor for Leopard.
     *
     * @param row
     *         initial row number
     * @param col
     *         initial column number
     * @param player
     *         the player it belongs to
     */
    public Leopard(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 5;
        this.type = JungleType.LEOPARD;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "LP";
            case CHINESE_SIMPLE, CHINESE_TRADITIONAL -> "豹";
        };
    }
}
