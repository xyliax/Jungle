package group11.comp3211.common.exceptions;

import group11.comp3211.controller.GameManager;
import group11.comp3211.model.Loader;
import group11.comp3211.model.Movable;

/**
 *  Throws an exception if cannot be loaded
 *  For Logical judgement with front and back end communication
 */
public final class NotLoadableException extends IllegalMovementException {
    public NotLoadableException(Loader loader, Movable movable) {
        super(String.format("%s cannot move onto %s!",
                movable.getSymbol(GameManager.getInstance().getGame().getLanguage()), loader.getType().name()));
    }
}
