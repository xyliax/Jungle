package group11.comp3211.common.exceptions;

import group11.comp3211.controller.GameManager;
import group11.comp3211.model.Movable;


/**
 *  Throws an exception if it cannot be caught
 *  For Logical judgement with front and back end communication
 */
public final class IllegalCaptureException extends LogicException {
    public IllegalCaptureException(Movable captor, Movable captured) {
        super(String.format("%s cannot capture %s!",
                captor.getSymbol(GameManager.getInstance().getGame().getLanguage()),
                captured.getSymbol(GameManager.getInstance().getGame().getLanguage())));
    }
}
