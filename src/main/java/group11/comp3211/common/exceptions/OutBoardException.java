package group11.comp3211.common.exceptions;

import group11.comp3211.controller.GameManager;
import group11.comp3211.model.Movable;

public final class OutBoardException extends IllegalMovementException {
    public OutBoardException(Movable movable) {
        super(String.format("%s cannot move out board!",
                movable.getSymbol(GameManager.getInstance().getGame().getLanguage())));
    }
}
