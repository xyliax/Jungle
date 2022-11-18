package group11.comp3211.common.exceptions;

/**
 *  Transport message
 *  For Logical judgement with front and back end communication
 */
public abstract class JungleException extends Exception {
    public JungleException(String message) {
        super(message);
    }
}
