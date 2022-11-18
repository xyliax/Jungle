package group11.comp3211.common.exceptions;

/**
 *  Throws an exception if it cannot moved
 *  For Logical judgement with front and back end communication
 */
public abstract class IllegalMovementException extends LogicException {
    public IllegalMovementException(String message) {
        super(message);
    }
}
