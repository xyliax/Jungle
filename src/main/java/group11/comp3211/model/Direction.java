package group11.comp3211.model;

import lombok.Getter;

@Getter
public enum Direction {
    UP("^"), LEFT("<"), DOWN("v"), RIGHT(">"), STAY(null);
    String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }
}
