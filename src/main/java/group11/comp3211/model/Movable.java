package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.view.Language;

public interface Movable {
    String getSymbol(Language language);

    void move(int row, int col);

    JungleType getType();
}
