package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Dog extends Piece {
    public Dog(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 3;
        this.type = JungleType.DOG;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "DG";
            case CHINESE_SIMPLE -> "狗";
            case CHINESE_TRADITIONAL -> "狗";
        };
    }
}
