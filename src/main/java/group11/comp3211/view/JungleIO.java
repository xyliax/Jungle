package group11.comp3211.view;

import group11.comp3211.model.Game;
import group11.comp3211.model.Loader;
import group11.comp3211.model.landscape.Den;
import group11.comp3211.model.landscape.Landscape;
import group11.comp3211.model.landscape.River;
import group11.comp3211.model.landscape.Trap;
import group11.comp3211.model.piece.Piece;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Console;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import static group11.comp3211.view.Color.*;
import static java.lang.Thread.sleep;


@Getter
@Setter
public final class JungleIO {
    public static final int FRAME_DELAY = 10;
    public static final int KEY_DELAY = 200;
    public static final String WHITE_SPACE = " ";
    private static final String CLEAR_K_STR = "\b\b\b\b    \b\b\b\b";
    private static final String CLEAR_L_STR = "\r" + " ".repeat(64) + "\r";
    private Console console;
    private String promptStr;
    private Scanner scanner;
    private InputStream reader;
    private PrintStream writer;
    private boolean dRemap;

    @SneakyThrows
    private JungleIO() {
        this.console = System.console();
        this.promptStr = "\033[33m>>> \033[0m";
        this.scanner = new Scanner(System.in);
        this.reader = System.in;
        this.writer = System.out;
        this.dRemap = false;
    }

    public static JungleIO getInstance() {
        return JungleIOHolder.JUNGLE_IO;
    }

    public synchronized void showLoadingAnimation() {
        Thread skip = new Thread(() -> getKey(false));
        skip.start();
        Thread loading = new Thread(() -> {
            announce("Press any KEY", YELLOW);
            for (int i = 0; i < 80; i++) {
                reset();
                hideCursor();
                setBold();
                print("\r");
                setBack(RED);
                for (int j = 0; j < i; j++)
                    print(WHITE_SPACE);
                if (!insertFrameDelay(8)) return;
            }
            skip.interrupt();
        });
        loading.start();
        try {
            skip.join();
            loading.interrupt();
        } catch (InterruptedException ignored) {
        } finally {
            reset();
        }
    }

    public synchronized void showWelcomeAnimation() {
        clearScreen();
        reset();
        hideCursor();
        setBack(GREY);
        Color front = Color.values()[new Random().nextInt(Color.values().length)];
        while (front == GREY || front == Color.BLACK)
            front = Color.values()[new Random().nextInt(Color.values().length)];
        setFront(front);
        setBold();
        int chCount = 0;
        for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) writer.print(' ');
                chCount = 0;
                insertFrameDelay(1);
            }
            writer.print(character);
        }
        insertKeyDelay();
        reset();
        print(WHITE_SPACE.repeat(27));
        print("PRESS");
        setFront(RED);
        setBold();
        setBlink();
        setUnderlined();
        print(" ENTER/RETURN ");
        reset();
        printLine("TO START!");
        print(WHITE_SPACE.repeat(18));
        setBack(GREY);
        setBold();
        printLine("Tips: You can always use Ctrl-C to quit Jungle.");
        reset();
    }

    public synchronized void showStartMenu(int select) {
        clearScreen();
        reset();
        hideCursor();
        int chCount = 0;
        int opt = 0;
        for (char character : JCString.START_MENU.string.toCharArray()) {
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) print(WHITE_SPACE);
                chCount = 0;
            }
            switch (character) {
                case '$' -> {
                    setBack(CYAN);
                    setFront(GREY);
                    setBold();
                }
                case '%', '@' -> reset();
                case '#' -> {
                    if (opt == select) {
                        setFront(RED);
                        setBold();
                        setBack(YELLOW);
                    }
                    opt++;
                }
                case '|' -> {
                    setFront(BLUE);
                    setBold();
                }
                default -> writer.print(character);
            }
        }
        reset();
    }

    public synchronized void showPlayBoard(Game game) {
        setCursor(0, 0);
        reset();
        for (int r = 1; r <= 19; r++) {
            if (r % 2 == 1) {
                for (int c = 1; c <= 46; c++) {
                    reset();
                    setBack(GREY);
                    print(WHITE_SPACE);
                }
            } else {
                for (int c = 1; c <= 46; c++) {
                    reset();
                    if (c <= 3 || c >= 44) {
                        setBack(GREY);
                        print(WHITE_SPACE);
                    } else {
                        if (c % 6 != 2 && c % 6 != 3) {
                            Loader block = game.getPlayboard().get(r / 2 - 1, (c - 4) / 6);
                            showBlock(block, game);
                            c += 3;
                        } else {
                            setBack(GREY);
                            print(WHITE_SPACE);
                        }
                    }
                }
            }
            writer.println();
        }
    }

    private synchronized void showBlock(Loader block, Game game) {
        reset();
        Landscape landscape = (Landscape) block;

        if (landscape instanceof Den den) {
            if (den.getPlayer() == game.getPlayerX()) setFront(RED);
            else if (den.getPlayer() == game.getPlayerY()) setFront(GREEN);
            setBold();
            print(WHITE_SPACE + den.getSymbol(game.getLanguage()) + WHITE_SPACE);
            return;
        } else if (landscape instanceof Trap) setBack(MAGENTA);
        else if (landscape instanceof River) setBack(CYAN);

        if (landscape.getLoad() == null && game.getSelectedPiece() != null) {
            Piece selectedPiece = game.getSelectedPiece();
            int tRow = selectedPiece.getRow(), tCol = selectedPiece.getCol();
            switch (selectedPiece.getDirection()) {
                case UP -> tRow--;
                case LEFT -> tCol--;
                case DOWN -> tRow++;
                case RIGHT -> tCol++;
                case STAY -> tRow = tCol = -1;
            }
            if (tRow == landscape.getRow() && tCol == landscape.getCol()) {
                setFront(YELLOW);
                setBlink();
                print(selectedPiece.getDirection().getSymbol().repeat(4));
                return;
            }
        }

        Piece piece = (Piece) landscape.getLoad();
        if (piece == null) print(WHITE_SPACE.repeat(4));
        else {
            if (game.getSelectedPiece() == piece) {
                setBack(YELLOW);
                if (piece.isSelected()) {
                    setBold();
                    setUnderlined();
                }
            }
            if (piece.getPlayer() == game.getPlayerX()) setFront(RED);
            else if (piece.getPlayer() == game.getPlayerY()) setFront(GREEN);
            print(WHITE_SPACE + piece.getSymbol(game.getLanguage()) + WHITE_SPACE);
        }
    }

    public void showExitMessage(String reason) {
        announce("Exit Jungle: " + reason, BLUE);
        reset();
    }

    public void announce(String msg, Color color) {
        reset();
        writer.println();
        setBack(GREY);
        setFront(MAGENTA);
        setBold();
        setUnderlined();
        writer.print("[SYSTEM NOTICE]");
        reset();
        writer.println();
        setFront(color);
        writer.print(msg);
        reset();
        writer.println();
        setBack(GREY);
        setFront(MAGENTA);
        setBold();
        setUnderlined();
        writer.print("[END OF NOTICE]");
        reset();
        writer.println();
    }

    @SneakyThrows
    public synchronized String readLine(String preload) {
        StringBuilder line = new StringBuilder(preload);
        char buf0;
        do {
            writer.print(promptStr);
            writer.print(preload);
            boolean lb = false;
            while (!lb) {
                while (reader.available() > 0) reader.read();
                buf0 = (char) reader.read();
                switch (buf0) {
                    case 27 -> {
                        writer.print(CLEAR_L_STR);
                        line.delete(0, line.length());
                        writer.print(promptStr);
                    }
                    case 127 -> {
                        writer.print(CLEAR_L_STR);
                        if (line.length() > 0)
                            line.deleteCharAt(line.length() - 1);
                        writer.print(promptStr);
                        writer.print(line);
                    }
                    case '\n' -> lb = true;
                    default -> line.append(buf0);
                }
            }
        } while (line.toString().isBlank());
        return line.toString();
    }

    @SneakyThrows
    public char getKey(boolean echo) {
        hideCursor();
        char buf0;
        while (reader.available() > 0) reader.read();
        buf0 = (char) reader.read();
        if (!echo) writer.print(CLEAR_K_STR);
        if (buf0 == 27) {
            if (reader.available() > 0 && reader.read() == '[') {
                char m = switch (reader.read()) {
                    case 'A' -> 'w';
                    case 'B' -> 's';
                    case 'C' -> 'd';
                    case 'D' -> 'a';
                    default -> 27;
                };
                reset();
                return m;
            } else return getKey(echo);
        }
        reset();
        return buf0;
    }

    /**
     * Prints a string. Act exactly the same as writer.print(String).
     *
     * @param string the {@code String} to be printed
     * @see java.io.PrintStream#print(String)
     */
    public void print(String string) {
        writer.print(string);
    }

    /**
     * Prints a string and then terminates the line.
     *
     * @param line the {@code String} to be printed
     * @see java.io.PrintStream#print(String)
     */
    public void printLine(String line) {
        writer.println(line);
    }

    private boolean insertFrameDelay(long times) {
        try {
            sleep(FRAME_DELAY * times);
        } catch (InterruptedException interruptedException) {
            return false;
        }
        return true;
    }

    private void insertKeyDelay() {
        try {
            sleep(KEY_DELAY);
        } catch (InterruptedException ignored) {
        }
    }

    public void clearScreen() {
        writer.print("\033c");
    }

    /**
     * Reset all console attributes set by {@link #setFront(Color)}, {@link #setBack(Color)}, {@link #setBold()},
     * {@link #setDim()}, {@link #setUnderlined()}, {@link #setBlink()}, {@link #hideCursor()}, {@link #showCursor()}.
     * <br>
     * {@link #setCursor(int, int)} cannot be reset by this method.
     */
    public void reset() {
        print("\033[0m");
        showCursor();
    }

    public void setFront(Color color) {
        print("\033[3" + color.value + "m");
    }

    public void setBack(Color color) {
        print("\033[4" + color.value + "m");
    }

    public void setBold() {
        print("\033[1m");
    }

    public void setDim() {
        print("\033[2m");
    }

    public void setUnderlined() {
        print("\033[4m");
    }

    public void setBlink() {
        print("\033[5m");
    }

    public void setCursor(int x, int y) {
        print("\033[" + y + ";" + x + "H");
    }

    public void hideCursor() {
        print("\033[?25l");
    }

    public void showCursor() {
        print("\033[?25h");
    }

    private static final class JungleIOHolder {
        private static final JungleIO JUNGLE_IO = new JungleIO();
    }
}