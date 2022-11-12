package group11.comp3211.controller;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.common.exceptions.VoidObjectException;
import group11.comp3211.model.Game;
import group11.comp3211.model.piece.Piece;
import group11.comp3211.view.Color;
import group11.comp3211.view.JungleIO;
import sun.misc.Signal;

import java.util.Date;
import java.util.Random;

import static group11.comp3211.model.Direction.*;
import static group11.comp3211.view.Color.BLUE;
import static group11.comp3211.view.Color.RED;


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


    private void operatingSystemCheck() {
        String OS = System.getProperty("os.name");
        String ARCH = System.getProperty("os.arch");
        if (OS.matches("Windows*")) {
            io.setFront(RED);
            io.printLine("[JUNGLE WARNING]");
            io.setFront(RED);
            io.printLine(String.format("Your OS is %s - %s, some features are limited", OS, ARCH));
            io.printLine("We STRONGLY recommend you to use Linux / MacOS for compatibility concerns");
            io.printLine("You will not receive this message any more, Continue in 10 Seconds...");
            io.setFront(RED);
            io.printLine("[END OF WARNING]");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            io.setFront(Color.GREEN);
            io.printLine(String.format("Your OS is %s - %s", OS, ARCH));
            io.printLine("Full features unlocked!");
            Signal.handle(new Signal("INT"), handle -> exit("SIGINT received at " + new Date()));
            io.showLoadingAnimation();
        }
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
            }
        } while (key != '\n');
        switch (select) {
            case 0 -> createNewGame();
            case 1 -> loadSavedGame();
            case 2 -> manual();
            case 3 -> exit(String.format("QUIT GAME from Start Menu - %s", new Date()));
        }
    }

    private void createNewGame() {
        io.clearScreen();
        this.game = new Game();
        io.announce("""
                Creating New Game...
                Follow the instructions         ***
                No longer than '8' characters   ***
                You may edit the preload name   ***
                To clear a character    click 'BACKSPACE'
                To clear a line         click 'ESCAPE'""", BLUE);
        io.reset();
        String nameX, nameY;
        do {
            io.setFront(game.getPlayerX().getColor());
            io.printLine(" - Please Input Player 1's Username");
            nameX = io.readLine("userX");
            if (nameX.length() > 8) io.announce("The username should no longer than '8' characters!", BLUE);
        } while (nameX.length() > 8);
        do {
            io.setFront(game.getPlayerY().getColor());
            io.printLine(" - Please Input Player 2's Username");
            nameY = io.readLine("userY");
            if (nameY.length() > 8) io.announce("The username should no longer than '8' characters!", BLUE);
        } while (nameY.length() > 8);
        game.getPlayerX().setName(nameX);
        game.getPlayerY().setName(nameY);
        io.reset();
        runGame();
    }

    private void loadSavedGame() {

    }

    private void runGame() {
        game.setRunning(true);
        game.setCurrentPlayer(new Random().nextInt() % 2 == 1 ? game.getPlayerX() : game.getPlayerY());
        io.setDRemap(true);
        char key = '-';
        while (game.isRunning()) {
            io.clearScreen();
            io.showPlayBoard(game);
            try {
                switch (key) {
                    case ':' -> pauseGame();
                    case '-', '0' -> game.clearSelectStatus();
                    default -> {
                        if (game.getSelectedPiece() == null) game.selectPiece(key);
                        else {
                            Piece piece = game.getSelectedPiece();
                            if (key == ' ') piece.setSelected(!piece.isSelected());
                            else if (piece.isSelected()) {
                                switch (key) {
                                    case 'w', 'W' -> piece.setDirection(UP);
                                    case 'a', 'A' -> piece.setDirection(LEFT);
                                    case 's', 'S' -> piece.setDirection(DOWN);
                                    case 'd', 'D' -> piece.setDirection(RIGHT);
                                    case '\n' -> game.runTurn();
                                }
                            } else game.selectPiece(key);
                        }
                    }
                }
            } catch (VoidObjectException voidObjectException) {
                io.announce(String.format("Unknown Piece Key '%c'", key), game.getCurrentPlayer().getColor());
                game.clearSelectStatus();
            } catch (LogicException logicException) {
                io.announce(logicException.toString(), game.getCurrentPlayer().getColor());
            } finally {
                io.showPlayBoard(game);
                key = io.getKey(true);
            }
        }
        io.setDRemap(false);
    }

    private void pauseGame() {
        io.announce("""
                You are attempting to leave.
                Save and Quit:      ' w '
                Do not Save:        ' q '
                Back to Game:       ' : '""", BLUE);
        char key;
        do {
            key = io.getKey(false);
            switch (key) {
                case 'w', 'W' -> {
                    String fileName = String.format("%s-%s.game", game.getPlayerX().getName(), game.getPlayerY().getName());
                    io.printLine("Use default file name?");
                    fileName = io.readLine(fileName);
                    game.saveToFile(fileName);
                    exit(String.format("SAVE and QUIT GAME - %s - %s", fileName, new Date()));
                }
                case 'q', 'Q' -> exit("QUIT GAME without SAVE");
            }
        } while (key != ':');
        io.clearScreen();
    }

    private void settlement() {

    }

    private void manual() {

    }

    private void exit(String reason) {
        io.showExitMessage(reason);
        System.exit(0);
    }

    private static final class GameManagerHolder {
        private static final GameManager GAME_MANAGER = new GameManager();
    }
}
