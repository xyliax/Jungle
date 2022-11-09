package group11.comp3211.view;

import group11.comp3211.model.Game;
import group11.comp3211.model.Loader;
import group11.comp3211.model.landscape.Den;
import group11.comp3211.model.landscape.Landscape;
import group11.comp3211.model.landscape.River;
import group11.comp3211.model.landscape.Trap;
import group11.comp3211.model.piece.*;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Console;
import java.io.IOException;
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
    private static final String CLEAR_K_STR = "\b\b\b\b    \b\b\b\b";
    private Console console;
    private String promptStr;
    private Scanner scanner;
    private InputStream reader;
    private PrintStream writer;

    @SneakyThrows
    private JungleIO() {
        console = System.console();
        promptStr = "\033[33m>>> \033[0m";
        scanner = new Scanner(System.in);
        reader = System.in;
        writer = System.out;
    }

    public static JungleIO getInstance() {
        return JungleIOHolder.JUNGLE_IO;
    }

    public void showLoadingAnimation() {
        for (int i = 0; i < 80; i++) {
            reset();
            hideCursor();
            setBold();
            print("\r");
            setBack(RED);
            for (int j = 0; j < i; j++)
                print(" ");
            insertFrameDelay(4);
            showCursor();
        }
        reset();
    }

    public void showWelcomeAnimation() {
        clearScreen();
        reset();
        hideCursor();
        setBack(GREY);
        Color front = Color.values()[new Random().nextInt(Color.values().length)];
        while (front == GREY || front == Color.BLACK)
            front = Color.values()[new Random().nextInt(Color.values().length)];
        setFront(front);
        int chCount = 0;
        for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) writer.print(" ");
                chCount = 0;
                insertFrameDelay(1);
            }
            writer.print(character);
        }
        insertKeyDelay();
        showCursor();
        reset();
        for (int i = 0; i < 27; i++)
            print(" ");
        print("PRESS");
        setFront(RED);
        setBold();
        setBlink();
        print(" ENTER/RETURN ");
        reset();
        printLine("TO START!");
        setBack(GREY);
        setBold();
        printLine("Tips: You can always use Ctrl-C to quit Jungle.");
        reset();
    }

    public void showStartMenu(int select) {
        clearScreen();
        reset();
        hideCursor();
        int chCount = 0;
        int opt = 0;
        for (char character : JCString.START_MENU.string.toCharArray()) {
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) print(" ");
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
        showCursor();
        reset();
    }

    public void showPlayBoard(Game game) {
        clearScreen();
        reset();
        for (int r = 1; r <= 19; r++) {
            if (r % 2 == 1) {
                for (int c = 1; c <= 32; c++) {
                    reset();
                    setBack(GREY);
                    print(" ");
                }
            } else {
                for (int c = 1; c <= 32; c++) {
                    reset();
                    if (c <= 3 || c >= 30) {
                        setBack(GREY);
                        print(" ");
                    } else {
                        if (c % 4 != 2 && c % 4 != 3) {
                            Loader block = game.getPlayboard().get(r / 2 - 1, c / 4 - 1);
                            showBlock(block, game);
                            c++;
                        } else {
                            setBack(GREY);
                            print(" ");
                        }
                    }
                }
            }
            printLine("");
        }
    }

    private void showBlock(Loader block, Game game) {
        reset();
        Landscape landscape = (Landscape) block;
        if (landscape instanceof River) setBack(CYAN);
        else if (landscape instanceof Trap) setBack(MAGENTA);
        else if (landscape instanceof Den) {
            if (((Den) landscape).getPlayer() == game.getPlayerX()) setFront(RED);
            else if (((Den) landscape).getPlayer() == game.getPlayerY()) setFront(GREEN);
            print("穴");
            return;
        }
        Piece piece = (Piece) landscape.getLoad();
        if (piece == null) print(" " + " ");
        else {
            if (piece.getPlayer() == game.getPlayerX()) setFront(RED);
            else if (piece.getPlayer() == game.getPlayerY()) setFront(GREEN);
            if (piece instanceof Elephant) print("象");
            else if (piece instanceof Lion) print("狮");
            else if (piece instanceof Tiger) print("虎");
            else if (piece instanceof Leopard) print("豹");
            else if (piece instanceof Wolf) print("狼");
            else if (piece instanceof Dog) print("狗");
            else if (piece instanceof Cat) print("猫");
            else if (piece instanceof Rat) print("鼠");
        }
    }

    public void showExitMessage(String reason) {
        clearScreen();
        reset();
        setFront(RED);
        setBold();
        printLine("Exit Jungle: " + reason);
        reset();
        showCursor();
        System.exit(0);
    }

    public String readLine() {
        String line;
        do {
            print(promptStr);
            line = scanner.nextLine();
        } while (line.isBlank());
        return line;
    }

    public synchronized void waitKey(char key) {
        hideCursor();
        if (Character.isAlphabetic(key)) key = Character.toLowerCase(key);
        try {
            while (System.in.available() > 0) System.in.read();
            int buf = reader.read();
            while ((char) buf != Character.toUpperCase(key)) {
                print(CLEAR_K_STR);
                buf = reader.read();
            }
            print(CLEAR_K_STR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            showCursor();
        }
    }

    public synchronized char getKey(boolean echo) {
        hideCursor();
        try {
            while (System.in.available() > 0) reader.read();
            char buf0 = (char) reader.read();
            if (!echo) print(CLEAR_K_STR);
            if (buf0 == 27) return getKey(echo);
            return buf0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            showCursor();
        }
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

    private void insertFrameDelay(int times) {
        try {
            sleep((long) FRAME_DELAY * times);
        } catch (InterruptedException ignored) {
        }
    }

    private void insertKeyDelay() {
        try {
            sleep(KEY_DELAY);
        } catch (InterruptedException ignored) {
        }
    }

    public void clearScreen() {
        if (console != null) writer.print("\033c");
    }

    /**
     * Reset all console attributes set by {@link #setFront(Color)}, {@link #setBack(Color)}, {@link #setBold()},
     * {@link #setDim()}, {@link #setUnderlined()}, {@link #setBlink()}.
     * <br>
     * {@link #hideCursor()}, {@link #showCursor()}, {@link #setCursor(int, int)} cannot be reset by this method.
     */
    public void reset() {
        print("\033[0m");
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