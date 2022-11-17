package group11.comp3211.controller;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.common.exceptions.VoidObjectException;
import group11.comp3211.model.Direction;
import group11.comp3211.model.Game;
import group11.comp3211.model.Player;
import group11.comp3211.model.piece.Piece;
import group11.comp3211.view.JungleIO;
import group11.comp3211.view.Language;
import lombok.SneakyThrows;
import sun.misc.Signal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Random;

import static group11.comp3211.model.Direction.*;
import static group11.comp3211.view.Color.*;


public final class GameManager {
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
            io.printLine("Please read README carefully!!!");
            io.printLine("Please read README carefully!!!");
            io.printLine("Please read README carefully!!!");
            io.printLine("Please read README carefully!!!");
            io.printLine("Please read README carefully!!!");
            System.exit(1);
        }
        String size = System.getenv("SIZE_J");
        int cRow = Integer.parseInt(size.split(" ")[0]);
        int cCol = Integer.parseInt(size.split(" ")[1]);
        if (cRow < 30 || cCol < 100) {
            io.announce(String.format("""
                    Console Size Unsatisfied!
                    Minimum Requirement:
                        %-3d (lines) %-3d (columns)
                    Current Size:
                        %-3d (lines) %-3d (columns)""", 30, 100, cRow, cCol), RED);
            System.exit(2);
        }
        io.setConsole_row(cRow);
        io.setConsole_col(cCol);
    }

    private void operatingSystemCheck() {
        String OS = System.getProperty("os.name");
        String ARCH = System.getProperty("os.arch");
        if (OS.matches("Windows*")) {
            io.setFront(RED);
            io.printLine("[JUNGLE WARNING]");
            io.setFront(RED);
            io.printLine(String.format("Your OS is %s - %s, some features are limited", OS, ARCH));
            io.printLine("We STRONGLY recommend you to use Linux / macOS for compatibility concerns");
            io.printLine("You will not receive this message any more, Continue in 10 Seconds...");
            io.setFront(RED);
            io.printLine("[END OF WARNING]");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            io.announce(String.format("""
                            Your OS is ' %s ' ARCH ' %s '
                            Console Window Size %d * %d
                            You are not recommended to resize window ***
                            Full features unlocked!!!""", OS, ARCH,
                    io.getConsole_row(), io.getConsole_col()), GREEN);
            Signal.handle(new Signal("INT"), handle -> exit("SIGINT received at " + new Date()));
            io.showLoadingAnimation();
        }
    }

    public void boot() {
        environmentCheck();
        operatingSystemCheck();
        welcome();
        startMenu();
    }

    @SneakyThrows
    public void debug(File gameFile) {
        FileInputStream fileInputStream = new FileInputStream(gameFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        game = (Game) objectInputStream.readObject();
        //runGame();
    }

    private void welcome() {
        do {
            io.showWelcomeAnimation();
        } while (io.getKey(false) != '\n');
    }

    private void startMenu() {
        int select = 0;
        char key;
        while (select != -1) {
            do {
                io.showStartMenu(select);
                io.setDRemap(true);
                key = io.getKey(true);
                io.setDRemap(false);
                switch (key) {
                    case 27 -> select = -1;
                    case ' ', '\t', 's' -> select = (select + 1) % 4;
                    case 'w' -> select = (select + 3) % 4;
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
        game.setRunning(true);
        game.setCurrentPlayer(new Random().nextInt() % 2 == 1 ? game.getPlayerX() : game.getPlayerY());
        game.setLanguage(Language.CHINESE_TRADITIONAL);
        runGame();
    }

    private void loadSavedGame() {
        String[] fileList = Game.getFileList();
        if (fileList == null || fileList.length == 0) {
            io.announce("No game files found!", RED);
            io.announce("Press any KEY", YELLOW);
            io.getKey(false);
            return;
        }
        int select = 0;
        char key;
        do {
            io.showSavedGames(fileList, select);
            io.setDRemap(true);
            key = io.getKey(true);
            io.setDRemap(false);
            switch (key) {
                case 27 -> select = -1;
                case ' ', '\t', 's' -> select = (select + 1) % fileList.length;
                case 'w' -> select = (select + fileList.length - 1) % fileList.length;
                default -> {
                    if (Character.isDigit(key)) {
                        int t = Integer.parseInt(String.valueOf(key));
                        if (t < fileList.length) select = t;
                    }
                }
            }
        } while (key != '\n' && select != -1);
        if (select == -1) return;
        try {
            game = Game.loadFromFile(fileList[select]);
        } catch (IOException | ClassNotFoundException e) {
            io.announce(String.format("File '%s' is broken!\nRetry or Start new Game!", fileList[select]), RED);
            return;
        }
        io.announce(String.format("""
                        Successfully load game from '%s'
                        Info: '%s' vs '%s'
                        Current Player '%s'
                        Press any KEY to start""", fileList[select],
                game.getPlayerX(), game.getPlayerY(), game.getCurrentPlayer()), BLUE);
        io.getKey(false);
        io.reset();
        runGame();
    }

    private void runGame() {
        Player winner = null;
        StringBuilder notice = new StringBuilder(String.format("Your Turn: %s\n", game.getCurrentPlayer()));
        io.clearScreen();
        io.showPlayBoard(game);
        io.showNoticeBoard(notice.toString());
        io.setDRemap(true);
        char key = '-';
        do {
            key = io.getKeyInGame(key);
            try {
                switch (key) {
                    case 27 -> {
                        return;
                    }
                    case ':' -> pauseGame();
                    case '-', '0', '\n' -> game.clearSelectStatus();
                    case '\t' -> game.setLanguage(switch (game.getLanguage()) {
                        case ENGLISH -> Language.CHINESE_TRADITIONAL;
                        case CHINESE_TRADITIONAL -> Language.ENGLISH;
                        default -> throw new IllegalStateException("Language Offline: " + game.getLanguage());
                    });
                    default -> {
                        Piece currentPiece = game.getSelectedPiece();
                        if (currentPiece != null) {
                            switch (key) {
                                case 'w', 'W' -> currentPiece.setDirection(UP);
                                case 'a', 'A' -> currentPiece.setDirection(LEFT);
                                case 's', 'S' -> currentPiece.setDirection(DOWN);
                                case 'd', 'D' -> currentPiece.setDirection(RIGHT);
                                default -> currentPiece.setDirection(STAY);
                            }
                        }
                        if (currentPiece != null && currentPiece.getDirection() != STAY) {
                            Direction direction = currentPiece.getDirection();
                            game.runTurn();
                            notice.delete(0, notice.length());
                            notice.append(String.format("%s: %s moves %s\n",
                                    currentPiece.getPlayer().getName(), currentPiece.getSymbol(game.getLanguage()),
                                    direction.name()));
                            io.announceInGame("", BLACK);
                        } else {
                            game.selectPieceByKey(key);
                            game.getSelectedPiece().setSelected(true);
                            notice.delete(0, notice.length());
                        }
                    }
                }
            } catch (VoidObjectException voidObjectException) {
                io.announceInGame(voidObjectException.getMessage(), RED);
                game.clearSelectStatus();
                notice.delete(0, notice.length());
            } catch (LogicException logicException) {
                io.announceInGame(logicException.getMessage(), BLUE);
                game.clearSelectStatus();
                notice.delete(0, notice.length());
            } finally {
                io.showPlayBoard(game);
                if (game.getSelectedPiece() != null)
                    notice.append(String.format("%s SELECT %s\n", game.getCurrentPlayer(),
                            game.getSelectedPiece().getSymbol(game.getLanguage())));
                notice.append(String.format("Your Turn: %s\n", game.getCurrentPlayer()));
                io.showNoticeBoard(notice.toString());
                notice.delete(0, notice.length());
                if ((winner = game.findWinner()) != null) game.setRunning(false);
            }
        } while (game.isRunning());
        if (winner != null) {
            notice.delete(0, notice.length());
            notice.append("Congratulations!!!\n");
            notice.append(String.format("%s WINS!!!\n", winner));
            io.announceInGame(String.format("Game Ends...\n%s Wins\nPress ENTER/RETURN", winner),
                    winner.getColor());
            io.showNoticeBoard(notice.toString());
            do {
                key = io.getKey(false);
            } while (key != '\n');
        }
        io.setDRemap(false);
    }

    private void pauseGame() {
        io.announceInGame("""
                Game Paused -------------
                Save and Quit  ==>  ' w '
                Quit           ==>  ' q '
                Back to Game   ==>  'ESC'
                """, BLUE);
        char key;
        do {
            key = io.getKey(false);
            switch (key) {
                case 'w', 'W' -> {
                    String fileName = String.format("%s-%s", game.getPlayerX().getName(), game.getPlayerY().getName());
                    io.printLine("Use default file name?");
                    fileName = io.readLine(fileName);
                    game.saveToFile(fileName + ".game");
                }
                case 'q', 'Q' -> game.setRunning(false);
            }
        } while (!(key == 27 || key == ':') && game.isRunning());
        io.clearScreen();
    }

    private void manual() {
        try {
            Runtime.getRuntime().exec(new String[]{"open", "https://blog.peiyuxing.xyz/Jungle/"});
        } catch (IOException ignored) {
        }
    }

    public Game getGame() {
        return game;
    }

    private void exit(String reason) {
        io.showExitMessage(reason);
        System.exit(0);
    }

    private static final class GameManagerHolder {
        private static final GameManager GAME_MANAGER = new GameManager();
    }
}
