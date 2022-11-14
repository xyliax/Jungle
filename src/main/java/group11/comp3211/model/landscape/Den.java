package group11.comp3211.model.landscape;

import group11.comp3211.model.JungleType;
import group11.comp3211.model.Player;
import group11.comp3211.view.Language;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class Den extends Landscape {
    Player player;

    public Den(int row, int col, Player player) {
        super(row, col);
        this.player = player;
        this.type = JungleType.DEN;
    }

    @Override
    public String getSymbol(Language language) {
        return switch (language) {
            case ENGLISH -> null;
            case CHINESE_SIMPLE -> "穴";
            case CHINESE_TRADITIONAL -> null;
            case EMOJI -> null;
        };
    }
}
