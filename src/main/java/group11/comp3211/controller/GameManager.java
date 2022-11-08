package group11.comp3211.controller;

import group11.comp3211.model.Game;
import group11.comp3211.view.Color;
import group11.comp3211.view.JungleIO;
import sun.misc.Signal;

import java.util.Date;

public final class GameManager {
    private static final char[] loadingStr = {'-', '\\', '|', '/'};
    private final JungleIO io;
    private Game game;

    private GameManager() {
        game = null;
        io = JungleIO.getInstance();
    }

    public static GameManager getInstance() {
        return GameManagerHolder.GAME_MANAGER;
    }

    /**
     * Check running environment. Any environmental checking should be performed in this method.
     */
    private void environmentCheck() {
        if (System.console() == null) {
            io.printLine("不要使用ide环境，仔细看README😅");
            System.exit(1);
        }
    }

    /**
     * Check host OS
     */
    private void operatingSystemCheck() {
        String OS = System.getProperty("os.name");
        String ARCH = System.getProperty("os.arch");
        io.clearScreen();
        if (OS.matches("Windows*")) {
            io.setFront(Color.RED);
            io.printLine("[JUNGLE WARNING]");
            io.setFront(Color.BLACK);
            io.printLine(String.format("Your OS is %s - %s, some features are limited", OS, ARCH));
            io.printLine("You will not receive this message any more");
            io.setFront(Color.RED);
            io.printLine("[END OF WARNING]");
        } else {
            io.setFront(Color.GREEN);
            io.printLine(String.format("Your OS is %s - %s", OS, ARCH));
            io.printLine("Full features unlocked!");
        }
        Signal.handle(new Signal("INT"), handle -> exit("SIGINT received at " + new Date()));
        io.showLoadingAnimation();
    }

    public void boot() {
        environmentCheck();
        operatingSystemCheck();
        welcomeBanner();
        startMenu();
    }

    private void welcomeBanner() {
        do {
            io.showWelcomeAnimation();
        } while (io.getKey(false) != '\n');
    }

    private void startMenu() {
        int select = 0;
        char key;
        do {
            io.showStartMenu(select);
            key = io.getKey(true);
            switch (key) {
                case ' ' -> select = (select + 1) % 4;
                case '1' -> select = 0;
                case '2' -> select = 1;
                case '3' -> select = 2;
                case '4' -> select = 3;
                default -> {
                }
            }
        } while (key != '\n');
        switch (select) {
            case 0 -> createNewGame();
            case 1 -> loadSavedGame();
            case 2 -> manual();
            case 3 -> exit("QUIT GAME from Start Menu");
        }
    }

    private void createNewGame() {
        io.setBold();
        io.setFront(Color.RED);
        io.printLine("Creating New Game...");
        io.printLine("Follow the instructions ***");
        io.printLine("In this mode, you cannot user 'BACKSPACE' to delete a character.");
        io.printLine("To re-enter, input a different string when 'Confirm'.");
        io.setFront(Color.GREEN);
        io.reset();
        String nameX, nameY, confirm;
        do {
            io.printLine(" - Please Input Player 1's Username");
            nameX = io.readLine();
            io.printLine(" - Confirm Player 1's Username (Enter Again)");
            confirm = io.readLine();
        } while (!confirm.equals(nameX));
        do {
            io.printLine(" - Please Input Player 2's Username");
            nameY = io.readLine();
            io.printLine(" - Confirm Player 2's Username (Enter Again)");
            confirm = io.readLine();
        } while (!confirm.equals(nameY));
        game = new Game(nameX, nameY);
        io.printLine(nameX);
        io.printLine(nameY);
    }

    private void loadSavedGame() {

    }

    private void runGame() {
        while (game.isRunning()) {
            //step 1 render view
            //step 2 get input
            //step 3 game.runTurn()
            //step 4 log
            //step 5 check winner -> setRunning()
        }
    }

    private void settlement() {

    }

    private void manual() {

    }

    private void exit(String reason) {
        io.showExitMessage(reason);
    }

    private static final class GameManagerHolder {
        private static final GameManager GAME_MANAGER = new GameManager();
    }
}
