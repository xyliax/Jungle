package group11.comp3211.model.piece;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.Direction;
import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Rat extends Piece {
    public Rat(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 1;
        this.type = JungleType.RAT;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> null;
            case CHINESE_SIMPLE -> "鼠";
            case CHINESE_TRADITIONAL -> null;
            case EMOJI -> null;
        };
    }
}
