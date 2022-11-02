package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;


@Getter
@Setter
public final class JungleIO {
    private Console console;
    private String promptStr;
    private String clearKStr;
    private Scanner scanner;
    private Reader reader;

    @SneakyThrows
    private JungleIO() {
        console = System.console();
        promptStr = ">>> ";
        clearKStr = "\b\b\b\b    \b\b\b\b";
        scanner = new Scanner(System.in);
        reader = new InputStreamReader(System.in);
    }

    public static JungleIO getInstance() {
        return JungleIOHolder.JUNGLE_IO;
    }

    public void clearScreen() {
        if (console != null) System.out.print("\033c");
    }

    public void reset() {
        System.out.print("\033[0m");
    }

    public void setFront(Color color) {
        System.out.print("\033[3" + color.value + "m");
    }

    public void setBack(Color color) {
        System.out.print("\033[4" + color.value + "m");
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

    public void showWelcomeAnimation() {
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
                    while (chCount++ < 100) {
                        System.out.print(" ");
                    }
                    chCount = 0;
                    sleep(20);
                }
                System.out.print(character);
            }
            sleep(500);
        } catch (InterruptedException ignored) {
        } finally {
            showCursor();
            reset();
        }
    }

    public void showStartMenu() {

    }

    public String readLine() {
        System.out.print(promptStr);
        return scanner.nextLine();
    }

    public void waitKey(char key) {
        hideCursor();
        try {
            int buf = reader.read();
            while ((char) buf != Character.toUpperCase(key) && (char) buf != Character.toLowerCase(key)) {
                System.out.print(clearKStr);
                buf = reader.read();
            }
            System.out.print(clearKStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            showCursor();
        }
    }

    public char getKey() {
        hideCursor();
        try {
            while (System.in.available() > 0)
                System.in.read();
            int buf = reader.read();
            System.out.print(clearKStr);
            return (char) buf;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            showCursor();
        }
    }


    public void print(String string) {
        System.out.print(string);
    }

    public void printLine(String line) {
        System.out.println(line);
    }

    private static final class JungleIOHolder {
        private static final JungleIO JUNGLE_IO = new JungleIO();
    }
}