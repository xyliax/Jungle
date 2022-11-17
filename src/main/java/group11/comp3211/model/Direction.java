package group11.comp3211.model;

import lombok.Getter;

/**
 * the direction of a piece
 */
@Getter
public enum Direction {
    /**
     * up
     */
    UP("^"),
    /**
     * left
     */
    LEFT("<"),
    /**
     * down
     */
    DOWN("v"),
    /**
     * right
     */
    RIGHT(">"),
    /**
     * stay
     */
    STAY(null);
    final String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }
}
