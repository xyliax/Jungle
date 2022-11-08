package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

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
        for (int i = 0; i < 72; i++) {
            reset();
            hideCursor();
            setBold();
            print("\rLoading ");
            setBack(Color.RED);
            for (int j = 0; j < i; j++)
                print(" ");
            insertFrameDelay();
            showCursor();
        }
        reset();
    }

    public void showWelcomeAnimation() {
        clearScreen();
        hideCursor();
        setBack(Color.GREY);
        Color front = Color.values()[new Random().nextInt(Color.values().length)];
        while (front == Color.GREY || front == Color.BLACK)
            front = Color.values()[new Random().nextInt(Color.values().length)];
        setFront(front);
        int chCount = 0;
        for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) {
                    writer.print(" ");
                }
                chCount = 0;
                insertFrameDelay();
            }
            writer.print(character);
        }
        insertKeyDelay();
        showCursor();
        reset();
        for (int i = 0; i < 27; i++)
            print(" ");
        print("PRESS");
        setFront(Color.RED);
        setBold();
        setBlink();
        print(" ENTER/RETURN ");
        reset();
        printLine("TO START!");
        setBack(Color.GREY);
        setBold();
        printLine("Tips: You can always use Ctrl-C to quit Jungle.");
        reset();
    }

    public void showStartMenu(int select) {
        clearScreen();
        hideCursor();
        int chCount = 0;
        int opt = 0;
        for (char character : JCString.START_MENU.string.toCharArray()) {
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) {
                    writer.print(" ");
                }
                chCount = 0;
            }
            switch (character) {
                case '$' -> {
                    setBack(Color.CYAN);
                    setFront(Color.GREY);
                    setBold();
                }
                case '%', '@' -> reset();
                case '#' -> {
                    if (opt == select) {
                        setFront(Color.RED);
                        setBold();
                        setBack(Color.YELLOW);
                    }
                    opt++;
                }
                case '|' -> {
                    setFront(Color.BLUE);
                    setBold();
                }
                default -> writer.print(character);
            }
        }
        showCursor();
        reset();
    }

    public void showExitMessage(String reason) {
        clearScreen();
        reset();
        setFront(Color.RED);
        setBold();
        printLine("Exit Jungle: " + reason);
        reset();
        showCursor();
        System.exit(0);
    }

    public String readLine() {
        String line;
        do {
            writer.print(promptStr);
            line = scanner.nextLine();
        } while (line.isBlank());
        return line;
    }

    public synchronized void waitKey(char key) {
        hideCursor();
        if (Character.isAlphabetic(key))
            key = Character.toLowerCase(key);
        try {
            while (System.in.available() > 0)
                System.in.read();
            int buf = reader.read();
            while ((char) buf != Character.toUpperCase(key)) {
                writer.print(CLEAR_K_STR);
                buf = reader.read();
            }
            writer.print(CLEAR_K_STR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            showCursor();
        }
    }

    public synchronized char getKey(boolean echo) {
        hideCursor();
        try {
            while (System.in.available() > 0)
                reader.read();
            char buf0 = (char) reader.read();
            if (!echo)
                writer.print(CLEAR_K_STR);
            if (buf0 == 27)
                return getKey(echo);
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

    private void insertFrameDelay() {
        try {
            sleep(FRAME_DELAY);
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
        writer.print("\033[0m");
    }

    public void setFront(Color color) {
        writer.print("\033[3" + color.value + "m");
    }

    public void setBack(Color color) {
        writer.print("\033[4" + color.value + "m");
    }

    public void setBold() {
        writer.print("\033[1m");
    }

    public void setDim() {
        writer.print("\033[2m");
    }

    public void setUnderlined() {
        writer.print("\033[4m");
    }

    public void setBlink() {
        writer.print("\033[5m");
    }

    public void setCursor(int x, int y) {
        writer.print("\033[" + y + ";" + x + "H");
    }

    public void hideCursor() {
        writer.print("\033[?25l");
    }

    public void showCursor() {
        writer.print("\033[?25h");
    }

    private static final class JungleIOHolder {
        private static final JungleIO JUNGLE_IO = new JungleIO();
    }
}