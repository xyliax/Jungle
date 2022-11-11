package group11.comp3211.model;

import group11.comp3211.view.Color;
import lombok.Data;

import java.io.Serializable;

@Data
public final class Player implements Serializable {
    private String name;
    private Color color;

    public Player(Color color) {
        this.color = color;
    }
}
