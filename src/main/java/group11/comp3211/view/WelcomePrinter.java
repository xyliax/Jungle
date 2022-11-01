package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

@Getter
@Setter
public final class WelcomePrinter implements Runnable {
    private static final int START_X = 1;
    private static final int START_Y = 1;
    private JungleIO io;
    private int x;
    private int y;

    public WelcomePrinter() {
        setIo(JungleIO.getInstance());
        setX(START_X);
        setY(START_Y);
    }

    @Override
    public void run() {
        io.clearScreen();
        io.hideCursor();
        try {
            while (!currentThread().isInterrupted()) {
                io.setBack(Color.GREY);
                for (Color color : Color.values()) {
                    if (color == Color.GREY) continue;
                    io.setFront(color);
                    setX(START_X);
                    setY(START_Y);
                    int chCount = 0;
                    for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
                        io.setCursor(x, y);
                        chCount++;
                        if (character == '\n') {
                            while (chCount++ < 100) {
                                io.setCursor(x, y);
                                System.out.print(" ");
                                x++;
                            }
                            chCount = 0;
                            sleep(15);
                        }
                        System.out.print(character);
                        x++;
                        if (character == '\n') {
                            x = 1;
                            y++;
                        }
                    }
                }
            }
        } catch (InterruptedException ignored) {
        } finally {
            io.clearScreen();
            io.showCursor();
            io.reset();
        }
    }
}
