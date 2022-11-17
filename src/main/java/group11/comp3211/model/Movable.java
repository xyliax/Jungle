package group11.comp3211.model;

import group11.comp3211.view.Language;

public interface Movable {
    String getSymbol(Language language);

    int getRow();

    int getCol();

    Direction getDirection();

    void move(int row, int col);

    JungleType getType();
}
