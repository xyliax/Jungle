package group11.comp3211.model.landscape;

import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Den extends Landscape {
    Player player;

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> null;
            case CHINESE_SIMPLE -> "穴";
            case CHINESE_TRADITIONAL -> null;
            case EMOJI -> null;
        };
    }

    public Den(int row, int col, Player player) {
        super(row, col);
        this.player = player;
    }
}
