package group11.comp3211.model;

import group11.comp3211.view.Language;

public interface Loader {
    String getSymbol(Language language);

    Movable getLoad();

    void setLoad(Movable movable);

    boolean canLoad(Movable movable);

    JungleType getType();
}
