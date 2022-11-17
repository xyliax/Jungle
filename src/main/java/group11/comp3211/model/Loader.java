package group11.comp3211.model;

import group11.comp3211.view.Language;

/**
 * Stuff that can load a movable on the board.
 */
public interface Loader {
    /**
     * @param language language type
     * @return Symbolic representation of a movable
     */
    String getSymbol(Language language);

    /**
     * Get the load.
     * @return the loaded movable
     */
    Movable getLoad();

    /**
     * Set the load.
     * @param movable the movable to load
     */
    void setLoad(Movable movable);

    /**
     * Check whether the loader can load a certain movable.
     * @param movable the movable to check
     * @return can or cannot
     */
    boolean canLoad(Movable movable);

    /**
     * Get the actual class name for the loader instance.
     * @return DEN / LAND / TRAP / WATER
     */
    JungleType getType();
}
