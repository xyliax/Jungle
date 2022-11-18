package group11.comp3211.model;

import group11.comp3211.view.Language;

/**
 * stuff that can move on the board, such as pieces
 */
public interface Movable {
    /**
     * @param language
     *         language type
     *
     * @return Symbolic representation of a movable
     */
    String getSymbol(Language language);

    /**
     * Get row number.
     *
     * @return row number
     */
    int getRow();

    /**
     * Get column number.
     *
     * @return column number
     */
    int getCol();

    /**
     * Get the direction.
     *
     * @return UP / DOWN / LEFT / RIGHT / STAY
     */
    Direction getDirection();

    /**
     * Move to a certain coordinate.
     *
     * @param row
     *         destination row number
     * @param col
     *         destination column number
     */
    void move(int row, int col);

    /**
     * Get the actual class name for the movable instance.
     *
     * @return CAT / DOG / ELEPHANT / ...
     */
    JungleType getType();
}
