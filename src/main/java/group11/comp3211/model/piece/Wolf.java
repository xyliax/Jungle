package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Wolf extends Piece {
    public Wolf(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 4;
        this.type = JungleType.WOLF;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "WF";
            case CHINESE_SIMPLE -> "狼";
            case CHINESE_TRADITIONAL -> "狼";
        };
    }
}
