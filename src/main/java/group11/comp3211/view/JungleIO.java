package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Scanner;


@Getter
@Setter
public final class JungleIO {
    private Console console;
    private String promptStr;
    private Scanner scanner;
    private BufferedReader bufferedReader;

    @SneakyThrows
    private JungleIO() {
        console = System.console();
        promptStr = ">>> ";
        scanner = new Scanner(System.in);
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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

    @Deprecated
    public void setBlink() {
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

    public void interceptSignal(String signal, Method method) {
//        method = ;
//        Signal.handle(new Signal(signal), handler -> {
//            method.invoke()
//        })
    }

    @SneakyThrows
    public void waitKey(char key) {
        int buf = bufferedReader.read();
        while ((char) buf != Character.toUpperCase(key) && (char) buf != Character.toLowerCase(key)) {
            buf = bufferedReader.read();
        }
    }

    public void printLine(String line) {
        System.out.println(line);
    }

    private static final class JungleIOHolder {
        private static final JungleIO JUNGLE_IO = new JungleIO();
    }
}