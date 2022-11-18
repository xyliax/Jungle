package group11.comp3211.common.exceptions;

import group11.comp3211.controller.GameManager;
import group11.comp3211.model.JungleType;
import group11.comp3211.model.Movable;

/**
 * Throws an exception if it cannot jump
 * For Logical judgement with front and back end communication
 */
public final class JumpingException extends IllegalMovementException {
    public JumpingException(Movable movable, JungleType jungleType) {
        super(String.format("%s cannot jump across %s!",
                movable.getSymbol(GameManager.getInstance().getGame().getLanguage()), jungleType));
    }
}
