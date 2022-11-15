package group11.comp3211.model.piece;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Leopard extends Piece {
    public Leopard(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 5;
        this.type = JungleType.LEOPARD;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> "LP";
            case CHINESE_SIMPLE -> "豹";
            case CHINESE_TRADITIONAL -> "豹";
        };
    }
}
