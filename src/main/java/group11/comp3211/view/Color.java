package group11.comp3211.view;


/**
 * The enum Color.
 */
public enum Color {
    /**
     * Black color.
     */
    BLACK(0),
    /**
     * Red color.
     */
    RED(1),
    /**
     * Green color.
     */
    GREEN(2),
    /**
     * Yellow color.
     */
    YELLOW(3),
    /**
     * Blue color.
     */
    BLUE(4),
    /**
     * Magenta color.
     */
    MAGENTA(5),
    /**
     * Cyan color.
     */
    CYAN(6),
    /**
     * Grey color.
     */
    GREY(7),
    /**
     * White color.
     */
    WHITE(107);
    /**
     * The Value.
     */
    final int value;

    Color(int value) {
        this.value = value;
    }
}
