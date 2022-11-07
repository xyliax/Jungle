package group11.comp3211.controller;

import group11.comp3211.model.Game;
import group11.comp3211.view.Color;
import group11.comp3211.view.JungleIO;
import lombok.SneakyThrows;

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
        osCheck();
    }

    /**
     * Check host OS
     */
    @SneakyThrows
    private void osCheck() {
        String OS = System.getProperty("os.name");
        io.clearScreen();
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
        for (int i = 0; i < 72; i++) {
            io.reset();
            io.hideCursor();
            io.setBold();
            io.print("\rLoading ");
            io.setBack(Color.RED);
            for (int j = 0; j < i; j++)
                io.print(" ");
            Thread.sleep(50);
            io.showCursor();
        }
        io.reset();
    }

    public void boot() {
        environmentCheck();
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
            if (key == ' ') {
                select = (select + 1) % 4;
            }
        } while (key != '\n');
    }

    private void createNewGame() {
        game = new Game();
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

    private void exit() {

    }

    private static final class GameManagerHolder {
        private static final GameManager GAME_MANAGER = new GameManager();
    }
}
