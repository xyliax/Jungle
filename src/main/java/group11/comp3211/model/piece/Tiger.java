package group11.comp3211.model.piece;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.Direction;
import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;

public final class Tiger extends Piece {
    public Tiger(int row, int col, Player player) {
        super(row, col, player);
        this.rank = 6;
        this.type = JungleType.TIGER;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> null;
            case CHINESE_SIMPLE -> "虎";
            case CHINESE_TRADITIONAL -> null;
            case EMOJI -> null;
        };
    }

    @Override
    public void move(Direction direction) throws LogicException {

    }
}
