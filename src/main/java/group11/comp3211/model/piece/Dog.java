package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Movable -> Piece -> Dog
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Dog extends Piece {
    /**
     * Constructor for Dog.
     *
     * @param row
     *         initial row number
     * @param col
     *         initial column number
     * @param player
     *         the player it belongs to
     */
    public Dog(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 3;
        this.type = JungleType.DOG;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "DG";
            case CHINESE_SIMPLE, CHINESE_TRADITIONAL -> "狗";
        };
    }
}
