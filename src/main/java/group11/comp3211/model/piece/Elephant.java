package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Elephant extends Piece {
    public Elephant(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 8;
        this.type = JungleType.ELEPHANT;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "EP";
            case CHINESE_SIMPLE -> "象";
            case CHINESE_TRADITIONAL -> "象";
        };
    }
}
