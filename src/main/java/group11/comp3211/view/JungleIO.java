package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Console;
import java.util.Scanner;

enum Color {
    BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), GREY(7);
    final int value;

    Color(int value) {
        this.value = value;
    }
}

@Getter
@Setter
public final class JungleIO {
    private Console console;
    private String promptStr;
    private Scanner scanner;

    private JungleIO() {
        setConsole(System.console());
        setPromptStr(">>> ");
        setScanner(new Scanner(System.in));
    }

    public static JungleIO getInstance() {
        return JungleIOHolder.JUNGLE_IO;
    }

    void clearScreen() {
        if (console != null) System.out.print("\033c");
    }

    void reset() {
        System.out.print("\033[0m");
    }

    void setFront(Color color) {
        System.out.print("\033[3" + color.value + "m");
    }

    void setBack(Color color) {
        System.out.print("\033[4" + color.value + "m");
    }

    void setCursor(int x, int y) {
        System.out.print("\033[" + y + ";" + x + "H");
    }

    void hideCursor() {
        System.out.print("\033[?25l");
    }

    void showCursor() {
        System.out.print("\033[?25h");
    }

    @Deprecated
    void setBlink() {
        //Cannot work on macOS
        System.out.print("\033[5m");
    }

    public Thread showWelcomeAnimation() {
        Thread welcomePrinterThread = new Thread(new WelcomePrinter(), "WELCOME");
        welcomePrinterThread.start();
        return welcomePrinterThread;
    }

    public Thread showStartMenu() {
        Thread startMenuThread = new Thread(new StartMenuPrinter(), "START_MENU");
        startMenuThread.start();
        return startMenuThread;
    }

    public String readLine() {
        System.out.print(promptStr);
        return scanner.nextLine();
    }

    public String readNoPrompt() {
        return scanner.nextLine();
    }

    public void printLine(String line) {
        System.out.println(line);
    }

    private static final class JungleIOHolder {
        private static final JungleIO JUNGLE_IO = new JungleIO();
    }
}