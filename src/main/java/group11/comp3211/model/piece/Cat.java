package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Movable -> Piece -> Cat
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Cat extends Piece {
    /**
     * Constructor for Cat.
     * @param row initial row number
     * @param col initial column number
     * @param player the player it belongs to
     */
    public Cat(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 2;
        this.type = JungleType.CAT;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "CT";
            case CHINESE_SIMPLE -> "猫";
            case CHINESE_TRADITIONAL -> "貓";
        };
    }
}
