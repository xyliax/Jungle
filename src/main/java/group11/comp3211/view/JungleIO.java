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


/**
 * The type Jungle io.
 */
@Getter
@Setter
public final class JungleIO {
    /**
     * The constant FRAME_DELAY.
     */
    public static final int FRAME_DELAY = 10;
    /**
     * The constant KEY_DELAY.
     */
    public static final int KEY_DELAY = 200;
    /**
     * The constant WHITE_SPACE.
     */
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


    private JungleIO() {
        this.console = System.console();
        this.promptStr = "\033[33m>>> \033[0m";
        this.scanner = new Scanner(System.in);
        this.reader = System.in;
        this.writer = System.out;
        this.dRemap = false;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JungleIO getInstance() {
        return JungleIOHolder.JUNGLE_IO;
    }

    /**
     * Gets char width.
     *
     * @param character the character
     *
     * @return the char width
     */
    public static int getCharWidth(char character) {
        int width;
        switch (character) {
            case '↑', '←', '↓', '→' -> width = 1;
            case '\n' -> width = 0;
            default -> {
                if (character >= 32 && character < 127)
                    width = 1;
                else
                    return 2;
            }
        }
        return width;
    }

    /**
     * Show loading animation.
     */
    public synchronized void showLoadingAnimation() {
        Thread loading = new Thread(() -> {
            announce("Press any KEY", YELLOW);
            print(setBack(GREY));
            print(setCursor(13, 0));
            writer.print(WHITE_SPACE.repeat(console_col));
            print(setCursor(15, 0));
            writer.print(WHITE_SPACE.repeat(console_col));
            print(setBack(RED));
            print(setCursor(14, 0));
            for (int i = 0; i < console_col; i++) {
                print(hideCursor());
                print(setBold());
                print("\r");
                for (int j = 0; j < i; j++)
                    writer.print(WHITE_SPACE);
                if (!insertFrameDelay(2))
                    return;
            }
        });
        Thread skip = new Thread(() -> {
            try {
                while (reader.available() <= 0)
                    sleep(10);
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
            print(reset());
        }
    }

    /**
     * Show welcome animation.
     */
    public synchronized void showWelcomeAnimation() {
        print(reset());
        clearScreen();
        print(hideCursor());
        print(setBack(GREY));
        Color front =
                Color.values()[new Random().nextInt(Color.values().length)];
        while (front == GREY || front == Color.BLACK)
            front = Color.values()[new Random().nextInt(Color.values().length)];
        print(setFront(front));
        print(setBold());
        int chCount = 0;
        StringBuilder lb = new StringBuilder();
        for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
            if (chCount == 0)
                lb.append(WHITE_SPACE.repeat((console_col - 80) / 2));
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80)
                    lb.append(' ');
                lb.append(WHITE_SPACE.repeat((console_col - 80) / 2));
                chCount = 0;
                insertFrameDelay(1);
                writer.print(lb);
                lb.delete(0, lb.length());
            }
            lb.append(character);
        }
        insertKeyDelay();
        print(reset());
        print(WHITE_SPACE.repeat((console_col - 28) / 2));
        print("PRESS");
        print(setFront(RED));
        print(setBold());
        print(setBlink());
        print(setUnderlined());
        print(" ENTER/RETURN ");
        print(reset());
        printLine("TO START!");
        print(WHITE_SPACE.repeat((console_col - 48) / 2));
        print(setBack(GREY));
        print(setBold());
        printLine("Tips: You can always use Ctrl-C to quit Jungle.");
        print(reset());
    }

    /**
     * Show start menu.
     *
     * @param select the select
     */
    public synchronized void showStartMenu(int select) {
        print(reset());
        clearScreen();
        print(hideCursor());
        int chCount = 0;
        int opt = 0;
        StringBuilder buffer = new StringBuilder();
        for (char character : JCString.START_MENU.string.toCharArray()) {
            if (chCount == 0)
                buffer.append(WHITE_SPACE.repeat((console_col - 80) / 2));
            chCount++;
            if (character == '\n') {
                while (chCount++ < 80)
                    buffer.append(WHITE_SPACE);
                buffer.append(WHITE_SPACE.repeat((console_col - 80) / 2));
                chCount = 0;
            }
            switch (character) {
                case '$' -> {
                    buffer.append("\033[46m");
                    buffer.append("\033[37m");
                    buffer.append("\033[1m");
                }
                case '%', '@' -> buffer.append("\033[0m");
                case '#' -> {
                    if (opt == select) {
                        buffer.append("\033[43m");
                        buffer.append("\033[31m");
                        buffer.append("\033[1m");
                    }
                    opt++;
                }
                case '|' -> {
                    buffer.append("\033[34m");
                    buffer.append("\033[1m");
                }
                default -> buffer.append(character);
            }
        }
        writer.print(buffer);
        writer.print(reset());
    }

    /**
     * Show play board.
     *
     * @param game the game
     */
    public synchronized void showPlayBoard(Game game) {
        print(reset());
        StringBuilder buffer = new StringBuilder();
        buffer.append(setCursor(0, 0));
        for (int r = 1; r <= 19; r++) {
            if (r % 2 == 1) {
                for (int c = 1; c <= 46; c++) {
                    buffer.append(reset());
                    buffer.append(setBack(GREY));
                    buffer.append(WHITE_SPACE);
                }
            } else {
                for (int c = 1; c <= 46; c++) {
                    buffer.append(reset());
                    if (c <= 3 || c >= 44) {
                        buffer.append(setBack(GREY));
                        buffer.append(WHITE_SPACE);
                    } else {
                        if (c % 6 != 2 && c % 6 != 3) {
                            Loader block = game.getPlayboard().get(r / 2 - 1,
                                    (c - 4) / 6);
                            showBlock(block, game, buffer);
                            c += 3;
                        } else {
                            buffer.append(setBack(GREY));
                            buffer.append(WHITE_SPACE);
                        }
                    }
                }
            }
            buffer.append("\n");
        }
        showKeyMapping(game.getLanguage(), buffer);
        writer.print(buffer);
        writer.print(reset());
    }

    private synchronized void showBlock(
            Loader block, Game game,
            StringBuilder buffer) {
        buffer.append(reset());
        switch (block.getType()) {
            case DEN -> {
                Den den = (Den) block;
                buffer.append(setBold());
                if (den.getLoad() == null) {
                    buffer.append(setFront(den.getPlayer().getColor()));
                    buffer.append(WHITE_SPACE)
                            .append(den.getSymbol(game.getLanguage()))
                            .append(WHITE_SPACE);
                } else {
                    Piece piece = (Piece) den.getLoad();
                    buffer.append(setBack(YELLOW));
                    buffer.append(setFront(piece.getPlayer().getColor()));
                    buffer.append(setBold());
                    buffer.append(WHITE_SPACE)
                            .append(piece.getSymbol(game.getLanguage()))
                            .append(WHITE_SPACE);
                }
                return;
            }
            case TRAP -> buffer.append(setBack(MAGENTA));
            case WATER -> buffer.append(setBack(BLUE));
            case LAND -> buffer.append(setBack(WHITE));
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
                buffer.append(setFront(YELLOW));
                buffer.append(setBlink());
                buffer.append(selectedPiece.getDirection().getSymbol().repeat(4));
                return;
            }
        }
        Piece piece = (Piece) landscape.getLoad();
        if (piece == null)
            buffer.append(WHITE_SPACE.repeat(4));
        else {
            if (game.getSelectedPiece() == piece) {
                buffer.append(setBack(YELLOW));
                if (piece.isSelected())
                    buffer.append(setUnderlined());
            }
            buffer.append(setBold());
            if (piece.getPlayer() == game.getPlayerX())
                buffer.append(setFront(RED));
            else if (piece.getPlayer() == game.getPlayerY())
                buffer.append(setFront(GREEN));
            buffer.append(WHITE_SPACE)
                    .append(piece.getSymbol(game.getLanguage()))
                    .append(WHITE_SPACE);
        }
    }

    /**
     * Show saved games.
     *
     * @param fileList the file list
     * @param select   the select
     */
    public synchronized void showSavedGames(String[] fileList, int select) {
        print(reset());
        clearScreen();
        print(hideCursor());
        announce("""
                SELECT GAME FILE TO LOAD
                Press 'ESC' -> Back to Menu""", CYAN);
        for (int fileId = 0; fileId < fileList.length; fileId++) {
            if (fileId == select) {
                print(setFront(RED));
                print(setBold());
                print(setBack(YELLOW));
            } else
                print(setBack(GREY));
            writer.printf("%-6d%s\n", fileId, fileList[fileId]);
        }
        print(reset());
    }

    /**
     * Show exit message.
     *
     * @param reason the reason
     */
    public void showExitMessage(String reason) {
        announceInGame("Exit Jungle: " + reason, BLUE);
        print(reset());
    }

    /**
     * Announce.
     *
     * @param msg   the msg
     * @param color the color
     */
    public void announce(String msg, Color color) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(reset());
        buffer.append("\n");
        buffer.append(setBold());
        buffer.append(setUnderlined());
        buffer.append("[SYSTEM NOTICE]");
        buffer.append(reset());
        buffer.append("\n");
        buffer.append(setBack(WHITE));
        buffer.append(setFront(color));
        buffer.append(msg);
        buffer.append(reset());
        buffer.append("\n");
        buffer.append(setBack(GREY));
        buffer.append(setFront(MAGENTA));
        buffer.append(setBold());
        buffer.append(setUnderlined());
        buffer.append("[END OF NOTICE]");
        buffer.append(reset());
        buffer.append("\n");
        writer.print(buffer);
    }

    /**
     * Announce in game.
     *
     * @param msg   the msg
     * @param color the color
     */
    public void announceInGame(String msg, Color color) {
        print(reset());
        print(setCursor(20, 0));
        for (int r = 20; r < console_row; r++)
            writer.println(WHITE_SPACE.repeat(60));
        print(setCursor(20, 0));
        announce(msg, color);
    }

    /**
     * Gets key in game.
     *
     * @param key the key
     *
     * @return the key in game
     */
    public char getKeyInGame(char key) {
        print(setCursor(18, 50));
        print(setBack(GREY));
        print(setFront(YELLOW));
        print(setUnderlined());
        writer.print("KEY ECHOING");
        print(reset());
        print(setBack(GREY));
        print(setUnderlined());
        print(setCursor(19, 50));
        switch (key) {
            case '\t' -> writer.print("\\t" + WHITE_SPACE.repeat(2));
            case '\n' -> print(setCursor(19, 50));
            default -> writer.print(key + WHITE_SPACE.repeat(3));
        }
        print(setCursor(19, 50));
        insertFrameDelay(1);
        return getKey(true);
    }

    /**
     * Show notice board.
     *
     * @param notice the notice
     */
    public synchronized void showNoticeBoard(String notice) {
        print(reset());
        int col = 50, row = 0;
        StringBuilder buffer = new StringBuilder();
        buffer.append(setCursor(++row, col));
        buffer.append(setBack(GREY));
        buffer.append(setFront(YELLOW));
        buffer.append(setBold());
        buffer.append(setUnderlined());
        buffer.append("[NOTICE BOARD]");
        buffer.append(reset());
        buffer.append(setCursor(++row, col));
        buffer.append(setBack(GREY));
        buffer.append(setFront(BLUE));
        int chCount = 0;
        for (char character : notice.toCharArray()) {
            if (character == '\n') {
                while (chCount++ < 40)
                    buffer.append(WHITE_SPACE);
                chCount = 0;
                buffer.append(setCursor(++row, col));
            } else {
                buffer.append(character);
                chCount += getCharWidth(character);
            }
        }
        while (row < 6) {
            buffer.append(WHITE_SPACE.repeat(40));
            buffer.append(setCursor(row++, col));
        }
        writer.print(buffer);
        writer.print(reset());
    }

    /**
     * Show key mapping.
     *
     * @param language the language
     */
    public synchronized void showKeyMapping(
            Language language,
            StringBuilder buffer) {
        buffer.append(reset());
        int row = 6, col = 50;
        buffer.append(setCursor(row, col));
        buffer.append(setBack(GREY));
        buffer.append(setFront(BLACK));
        buffer.append(setBold());
        int chCount = 0;
        char[] charArray = switch (language) {
            case CHINESE_TRADITIONAL, CHINESE_SIMPLE ->
                    JCString.KEY_MAPPING_CT.string.toCharArray();
            case ENGLISH -> JCString.KEY_MAPPING_EN.string.toCharArray();
        };
        for (char character : charArray) {
            if (character == '\n') {
                while (chCount++ < 40)
                    buffer.append(WHITE_SPACE);
                chCount = 0;
                buffer.append(setCursor(++row, col));
            } else
                buffer.append(character);
            chCount += getCharWidth(character);
        }
        buffer.append(reset());
    }

    /**
     * Read line string.
     *
     * @param preload the preload
     *
     * @return the string
     */
    @SneakyThrows
    public synchronized String readLine(String preload) {
        StringBuilder line = new StringBuilder(preload);
        char buf0;
        do {
            writer.print(promptStr);
            writer.print(preload);
            boolean lb = false;
            while (!lb) {
                while (reader.available() > 0)
                    reader.read();
                print(showCursor());
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
        print(hideCursor());
        return line.toString();
    }

    /**
     * Gets key.
     *
     * @param echo the echo
     *
     * @return the key
     */
    @SneakyThrows
    public char getKey(boolean echo) {
        char buf0;
        while (reader.available() > 0)
            reader.read();
        buf0 = (char) reader.read();
        if (!echo)
            writer.print(CLEAR_K_STR);
        else
            writer.print("\b" + buf0);
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
                    print(reset());
                    return m;
                }
            } else
                return getKey(echo);
        }
        print(reset());
        return buf0;
    }

    /**
     * Prints a string. Act exactly the same as writer.print(String).
     *
     * @param string the {@code String} to be printed
     *
     * @see java.io.PrintStream#print(String)
     *         java.io.PrintStream#print(String)
     */
    public void print(String string) {
        writer.print(string);
    }

    /**
     * Prints a string and then terminates the line.
     *
     * @param line the {@code String} to be printed
     *
     * @see java.io.PrintStream#print(String)
     *         java.io.PrintStream#print(String)
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

    /**
     * Clear screen.
     */
    public void clearScreen() {
        writer.print(hideCursor());
        writer.print(setCursor(0, 0));
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < console_row; i++) {
            buffer.append(WHITE_SPACE.repeat(console_col));
            buffer.append("\n");
        }
        writer.print(buffer);
        writer.flush();
        writer.print(setCursor(0, 0));
        //writer.print("\033c");
    }

    /**
     * Reset all console attributes set by {@link #setFront(Color)},
     * {@link #setBack(Color)}, {@link #setBold()},
     * {@link #setDim()}, {@link #setUnderlined()}, {@link #setBlink()},
     * {@link #hideCursor()}, {@link #showCursor()}.
     * <br>
     * {@link #setCursor(int, int)} cannot be reset by this method.
     */
    public String reset() {
        return "\033[0m";
    }

    /**
     * Sets front.
     *
     * @param color the color
     */
    public String setFront(Color color) {
        return "\033[3" + color.value + "m";
    }

    /**
     * Sets back.
     *
     * @param color the color
     */
    public String setBack(Color color) {
        if (color == WHITE)
            return "\033[107m";
        else
            return "\033[4" + color.value + "m";
    }

    /**
     * Sets bold.
     */
    public String setBold() {
        return "\033[1m";
    }

    /**
     * Sets dim.
     */
    public String setDim() {
        return "\033[2m";
    }

    /**
     * Sets underlined.
     */
    public String setUnderlined() {
        return "\033[4m";
    }

    /**
     * Sets blink.
     */
    public String setBlink() {
        return "\033[5m";
    }

    /**
     * Sets cursor.
     *
     * @param row the row
     * @param col the col
     */
    public String setCursor(int row, int col) {
        return "\033[" + row + ";" + col + "H";
    }

    /**
     * Hide cursor.
     */
    public String hideCursor() {
        return "\033[?25l";
    }

    /**
     * Show cursor.
     */
    public String showCursor() {
        return "\033[?25h";
    }

    private static final class JungleIOHolder {
        private static final JungleIO JUNGLE_IO = new JungleIO();
    }
}
