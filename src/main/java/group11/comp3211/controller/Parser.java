package group11.comp3211.controller;

public final class Parser {

    private Parser() {

    }

    public static Parser getInstance() {
        return InputHandlerHolder.RULE_CHECKER;
    }

    public void readOption() {

    }

    public void readCommand() {

    }

    private static final class InputHandlerHolder {
        private static final Parser RULE_CHECKER = new Parser();
    }
}
