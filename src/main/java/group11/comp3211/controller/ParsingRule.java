package group11.comp3211.controller;

import group11.comp3211.common.exceptions.UserInputException;

public enum ParsingRule {
    START_MENU;

    public boolean validate(String string) throws UserInputException {
        return true;
    }
}
