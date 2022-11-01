package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;

import static java.lang.Thread.sleep;

@Getter
@Setter
public final class StartMenuPrinter implements Runnable {
    private JungleIO io;
    private int x;
    private int y;

    public StartMenuPrinter() {
        setIo(JungleIO.getInstance());
        setX(1);
        setY(12);
    }

    @Override
    public void run() {
        io.setCursor(getX(), getY());
        try {
            setX(0);
            setY(12);
            int chCount = 0;
            for (char character : JCString.START_MENU.string.toCharArray()) {
                io.setCursor(x, y);
                chCount++;
                if (character == '\n') {
                    while (chCount++ < 100) {
                        io.setCursor(x, y);
                        System.out.print(" ");
                        x++;
                    }
                    chCount = 0;
                    sleep(25);
                }
                System.out.print(character);
                x++;
                if (character == '\n') {
                    x = 0;
                    y++;
                }
            }
        } catch (InterruptedException ignored) {
        } finally {
            io.reset();
        }
    }
}
