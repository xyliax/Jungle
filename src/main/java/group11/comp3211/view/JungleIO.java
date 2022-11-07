package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.*;
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

    @SneakyThrows
    private JungleIO() {
        console = System.console();
        promptStr = ">>> ";
        scanner = new Scanner(System.in);
        reader = System.in;
    }

    public static JungleIO getInstance() {
        return JungleIOHolder.JUNGLE_IO;
    }

    public synchronized void showWelcomeAnimation() {
        clearScreen();
        hideCursor();
        setBack(Color.GREY);
        Color front = Color.values()[new Random().nextInt(Color.values().length)];
        while (front == Color.GREY || front == Color.BLACK)
            front = Color.values()[new Random().nextInt(Color.values().length)];
        setFront(front);
        try {
            int chCount = 0;
            for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
                chCount++;
                if (character == '\n') {
                    while (chCount++ < 80) {
                        System.out.print(" ");
                    }
                    chCount = 0;
                    insertFrameDelay();
                }
                System.out.print(character);
            }
            sleep(KEY_DELAY);
        } catch (InterruptedException ignored) {
        } finally {
            showCursor();
            reset();
        }
        for (int i = 0; i < 22; i++)
            print(" ");
        print("PRESS");
        setFront(Color.RED);
        setBold();
        setBlink();
        print(" ENTER/RETURN ");
        reset();
        print("TO START JUNGLE!");
    }

    public synchronized void showStartMenu(int select) {
        clearScreen();
        hideCursor();
        try {
            int chCount = 0;
            for (char character : JCString.START_MENU.string.toCharArray()) {
                chCount++;
                if (character == '\n') {
                    while (chCount++ < 80) {
                        System.out.print(" ");
                    }
                    chCount = 0;
                    insertFrameDelay();
                }
                if (character == '$') {
                    setBack(Color.CYAN);
                    setFront(Color.GREY);
                } else if (character == '%')
                    reset();
                else System.out.print(character);
            }
            sleep(KEY_DELAY);
        } catch (InterruptedException ignored) {
        } finally {
            showCursor();
            reset();
        }
    }

    public String readLine() {
        System.out.print(promptStr);
        return scanner.nextLine();
    }

    public synchronized void waitKey(char key) {
        hideCursor();
        try {
            while (System.in.available() > 0)
                System.in.read();
            int buf = reader.read();
            while ((char) buf != Character.toUpperCase(key) && (char) buf != Character.toLowerCase(key)) {
                System.out.print(CLEAR_K_STR);
                buf = reader.read();
            }
            System.out.print(CLEAR_K_STR);
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
                System.out.print(CLEAR_K_STR);
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
     * Prints a string. Act exactly the same as System.out.print(String).
     *
     * @param string the {@code String} to be printed
     * @see java.io.PrintStream#print(String)
     */
    public void print(String string) {
        System.out.print(string);
    }

    /**
     * Prints a string and then terminates the line.
     *
     * @param line the {@code String} to be printed
     * @see java.io.PrintStream#print(String)
     */
    public void printLine(String line) {
        System.out.println(line);
    }

    /**
     *
     */
    public void insertFrameDelay() {
        try {
            sleep(FRAME_DELAY);
        } catch (InterruptedException ignored) {
        }
    }

    public void clearScreen() {
        if (console != null) System.out.print("\033c");
    }

    /**
     * Reset all console attributes set by {@link #setFront(Color)}, {@link #setBack(Color)}, {@link #setBold()},
     * {@link #setDim()}, {@link #setUnderlined()}, {@link #setBlink()}.
     * <br>
     * {@link #hideCursor()}, {@link #showCursor()}, {@link #setCursor(int, int)} cannot be reset by this method.
     */
    public void reset() {
        System.out.print("\033[0m");
    }

    public void setFront(Color color) {
        System.out.print("\033[3" + color.value + "m");
    }

    public void setBack(Color color) {
        System.out.print("\033[4" + color.value + "m");
    }

    public void setBold() {
        System.out.print("\033[1m");
    }

    public void setDim() {
        System.out.print("\033[2m");
    }

    public void setUnderlined() {
        System.out.print("\033[4m");
    }

    public void setBlink() {
        System.out.print("\033[5m");
    }

    public void setCursor(int x, int y) {
        System.out.print("\033[" + y + ";" + x + "H");
    }

    public void hideCursor() {
        System.out.print("\033[?25l");
    }

    public void showCursor() {
        System.out.print("\033[?25h");
    }

    private static final class JungleIOHolder {
        private static final JungleIO JUNGLE_IO = new JungleIO();
    }
}