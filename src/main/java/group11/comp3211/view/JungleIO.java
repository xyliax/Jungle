package group11.comp3211.view;

import group11.comp3211.model.Game;
import group11.comp3211.model.Loader;
import group11.comp3211.model.landscape.Den;
import group11.comp3211.model.landscape.Landscape;
import group11.comp3211.model.piece.Piece;
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
    public static final String WHITE_SPACE = " ";
    private static final String CLEAR_K_STR = "\b\b\b\b    \b\b\b\b";
    private static final String CLEAR_L_STR = "\r" + " ".repeat(64) + "\r";
    private int console_row;
    private int console_col;
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

    public static int getCharWidth(char character) {
        int width;
        switch (character) {
            case '↑', '←', '↓', '→' -> width = 1;
            default -> {
                if (character >= 32 && character < 127) width = 1;
                else return 2;
            }
        }
        return width;
    }

    public synchronized void showLoadingAnimation() {
        Thread loading = new Thread(() -> {
            announce("Press any KEY", YELLOW);
            setBack(GREY);
            setCursor(13, 0);
            writer.print(WHITE_SPACE.repeat(console_col));
            setCursor(15, 0);
            writer.print(WHITE_SPACE.repeat(console_col));
            setBack(RED);
            setCursor(14, 0);
            for (int i = 0; i < console_col; i++) {
                hideCursor();
                setBold();
                print("\r");
                for (int j = 0; j < i; j++)
                    writer.print(WHITE_SPACE);
                if (!insertFrameDelay(2)) return;
            }
        });
        Thread skip = new Thread(() -> {
            try {
                while (reader.available() <= 0) sleep(10);
                reader.read();
                loading.interrupt();
            } catch (IOException | InterruptedException ignored) {
            }
        });
        skip.start();
        loading.start();
        try {
            loading.join();
            skip.interrupt();
        } catch (InterruptedException ignored) {
        } finally {
            reset();
        }
    }

    public synchronized void showWelcomeAnimation() {
        reset();
        clearScreen();
        hideCursor();
        setBack(GREY);
        Color front = Color.values()[new Random().nextInt(Color.values().length)];
        while (front == GREY || front == Color.BLACK)
            front = Color.values()[new Random().nextInt(Color.values().length)];
        setFront(front);
        setBold();
        int chCount = 0;
        for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
            if (chCount == 0)
                writer.print(WHITE_SPACE.repeat((console_col - 80) / 2));
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) writer.print(' ');
                writer.print(WHITE_SPACE.repeat((console_col - 80) / 2));
                chCount = 0;
                insertFrameDelay(1);
            }
            writer.print(character);
        }
        insertKeyDelay();
        reset();
        print(WHITE_SPACE.repeat((console_col - 28) / 2));
        print("PRESS");
        setFront(RED);
        setBold();
        setBlink();
        setUnderlined();
        print(" ENTER/RETURN ");
        reset();
        printLine("TO START!");
        print(WHITE_SPACE.repeat((console_col - 48) / 2));
        setBack(GREY);
        setBold();
        printLine("Tips: You can always use Ctrl-C to quit Jungle.");
        reset();
    }

    public synchronized void showStartMenu(int select) {
        reset();
        clearScreen();
        hideCursor();
        int chCount = 0;
        int opt = 0;
        for (char character : JCString.START_MENU.string.toCharArray()) {
            if (chCount == 0)
                writer.print(WHITE_SPACE.repeat((console_col - 80) / 2));
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80) print(WHITE_SPACE);
                writer.print(WHITE_SPACE.repeat((console_col - 80) / 2));
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
        reset();
        setCursor(0, 0);
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
        showKeyMapping(game.getLanguage());
        reset();
    }

    private synchronized void showBlock(Loader block, Game game) {
        reset();
        switch (block.type()) {
            case DEN -> {
                Den den = (Den) block;
                setFront(den.getPlayer().getColor());
                setBold();
                print(WHITE_SPACE + den.getSymbol(game.getLanguage()) + WHITE_SPACE);
                return;
            }
            case TRAP -> setBack(MAGENTA);
            case WATER -> setBack(BLUE);
        }
        Landscape landscape = (Landscape) block;
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
                if (piece.isSelected()) setUnderlined();
            }
            setBold();
            if (piece.getPlayer() == game.getPlayerX()) setFront(RED);
            else if (piece.getPlayer() == game.getPlayerY()) setFront(GREEN);
            print(WHITE_SPACE + piece.getSymbol(game.getLanguage()) + WHITE_SPACE);
        }
    }

    public synchronized void showSavedGames(String[] fileList, int select) {
        reset();
        clearScreen();
        hideCursor();
        announce("SELECT GAME FILE TO LOAD", CYAN);
        for (int fileId = 0; fileId < fileList.length; fileId++) {
            if (fileId == select) {
                setFront(RED);
                setBold();
                setBack(YELLOW);
            } else setBack(GREY);
            writer.printf("%-6d%s\n", fileId, fileList[fileId]);
        }
        reset();
    }

    public void showExitMessage(String reason) {
        announceInGame("Exit Jungle: " + reason, BLUE);
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

    public void announceInGame(String msg, Color color) {
        reset();
        setCursor(20, 0);
        for (int r = 20; r < console_row; r++)
            writer.println(WHITE_SPACE.repeat(60));
        setCursor(20, 0);
        announce(msg, color);
    }

    public char getKeyInGame(char key) {
        setCursor(18, 50);
        setBack(GREY);
        setFront(YELLOW);
        setUnderlined();
        writer.print("KEY ECHOING");
        reset();
        setBack(GREY);
        setUnderlined();
        setCursor(19, 50);
        switch (key) {
            case '\t' -> writer.print("\\t" + WHITE_SPACE.repeat(2));
            case '\n' -> setCursor(19, 50);
            default -> writer.print(key + WHITE_SPACE.repeat(3));
        }
        setCursor(19, 50);
        return getKey(true);
    }

    public synchronized void showNoticeBoard(String notice) {
        reset();
        int col = 50, row = 0;
        setCursor(++row, col);
        setBack(GREY);
        setFront(YELLOW);
        setBold();
        setUnderlined();
        writer.print("[NOTICE BOARD]");
        reset();
        setCursor(++row, col);
        setBack(GREY);
        setFront(BLUE);
        setBold();
        int chCount = 0;
        for (char character : notice.toCharArray()) {
            if (character == '\n') {
                while (chCount++ < 50)
                    writer.print(WHITE_SPACE);
                chCount = 0;
                setCursor(++row, col);
            } else {
                writer.print(character);
                chCount++;
                if (character > 127) chCount++;
            }
        }
        while (row < 6) {
            writer.print(WHITE_SPACE.repeat(50));
            setCursor(row++, col);
        }
        reset();
    }

    public synchronized void showKeyMapping(Language language) {
        reset();
        int row = 6, col = 50;
        setCursor(row, col);
        setBack(GREY);
        setBold();
        int chCount = 0;
        char[] charArray = switch (language) {
            case CHINESE_TRADITIONAL, CHINESE_SIMPLE -> JCString.KEY_MAPPING_CT.string.toCharArray();
            case ENGLISH -> JCString.KEY_MAPPING_EN.string.toCharArray();
        };
        for (char character : charArray) {
            if (character == '\n') {
                while (chCount++ < 40) print(WHITE_SPACE);
                chCount = 0;
                setCursor(++row, col);
            } else writer.print(character);
            chCount++;
            if (character > 127) chCount++;
        }
        reset();
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
        else writer.print("\b" + buf0);
        if (buf0 == 27) {
            if (dRemap) {
                if (reader.available() > 0 && reader.read() == '[') {
                    char m = switch (reader.read()) {
                        case 'A', 'Z' -> 'w';
                        case 'B' -> 's';
                        case 'C' -> 'd';
                        case 'D' -> 'a';
                        default -> 27;
                    };
                    reset();
                    return m;
                }
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
        if (color == WHITE)
            print("\033[107m");
        else print("\033[4" + color.value + "m");
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

    public void setCursor(int row, int col) {
        print("\033[" + row + ";" + col + "H");
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