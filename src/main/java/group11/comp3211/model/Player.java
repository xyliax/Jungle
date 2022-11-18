package group11.comp3211.model;

import group11.comp3211.view.Color;
import lombok.Data;

import java.io.Serializable;

/**
 * A class of two instances.
 */
@Data
public final class Player implements Serializable {
    private String name;
    private Color color;

    /**
     * @param color
     *         the color of the player
     */
    public Player(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.color);
    }
}
