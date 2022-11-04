package group11.comp3211.controller;

import group11.comp3211.model.Game;
import group11.comp3211.view.Color;
import group11.comp3211.view.JungleIO;

public final class GameManager {
    private final Game game;
    private final JungleIO io;
    private final Parser parser;
    private char key;

    private GameManager() {
        game = null;
        io = JungleIO.getInstance();
        parser = Parser.getInstance();
        key = 0;
    }

    public static GameManager getInstance() {
        return GameManagerHolder.GAME_MANAGER;
    }

    public void envCheck() {
        if (System.console() == null) {
            io.printLine("不要使用ide环境，看README😅");
            System.exit(1);
        }
    }

    public void osCheck() {
        String OS = System.getProperty("os.name");
        if (OS.matches("Windows*")) {
            io.setFront(Color.RED);
            io.printLine("[JUNGLE WARNING]");
            io.setFront(Color.BLACK);
            io.printLine(String.format("Your OS is %s, some features are limited", OS));
            io.printLine("You will not receive this message any more");
            io.setFront(Color.RED);
            io.printLine("[END OF WARNING]");
        } else {
            io.setFront(Color.GREEN);
            io.printLine("Your OS is " + OS);
            io.printLine("Full features unlocked!");
        }
        io.reset();
    }

    public void boot() {
        envCheck();
        osCheck();
        while (key != 'a') {
            io.showWelcomeAnimation();
            /*
            PRESS ENTER
            Blinking Red "ENTER" (not guaranteed on customized environment)
             */
            io.print("PRESS ");
            io.setFront(Color.RED);
            io.setBold();
            io.setBlink();
            io.print("A (Try other keys!)");
            io.reset();
            /*
            Listen to KeyEvent until "a" is pressed
             */
            if ((key = io.getKey()) == 'a')
                break;
        }
    }

    public void startMenu() {
        io.showStartMenu();
    }

    public void createNewGame() {

    }

    public void loadSavedGame() {

    }

    public void runGame() {
        while (game.isRunning()) {
            //step 1 render view
            //step 2 get input
            //step 3 game.runTurn()
            //step 4 log
            //step 5 check winner -> setRunning()
        }
    }

    public void settlement() {

    }

    public void manual() {

    }

    public void exit() {

    }

    private static final class GameManagerHolder {
        private static final GameManager GAME_MANAGER = new GameManager();
    }
}
