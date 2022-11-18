package group11.comp3211.common.exceptions;

/**
 *  Throws an exception if wrong logic
 *  For Logical judgement with front and back end communication
 */
public class LogicException extends JungleException {

    public LogicException(String message) {
        super(message);
    }
}
