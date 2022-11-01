package group11.comp3211.controller;

import group11.comp3211.model.Game;
import group11.comp3211.view.Color;
import group11.comp3211.view.JungleIO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GameManager {
    private Game game;
    private JungleIO io;
    private Parser parser;
    private Thread welcomeAnimation;

    private GameManager() {
        setGame(null);
        setIo(JungleIO.getInstance());
        setParser(Parser.getInstance());
    }

    public static GameManager getInstance() {
        return GameManagerHolder.GAME_MANAGER;
    }

    public void OSCheck() {
        String OS = System.getProperty("os.name");
        if (OS.matches("Windows*")) {
            io.setFront(Color.RED);
            io.printLine("[JUNGLE WARNING]");
            io.setFront(Color.BLACK);
            io.printLine(String.format("Your OS is %s, some features are limited", OS));
            io.printLine("You will not receive this message any more");
            io.setFront(Color.RED);
            io.printLine("[END OF WARNING]");
        } else {
            io.setFront(Color.GREEN);
            io.printLine("Your OS is " + OS);
            io.printLine("Full features unlocked!");
        }
        io.reset();
    }

    public void boot() {
        OSCheck();
        //Thread animation = jungleIO.showWelcomeAnimation();
        io.waitKey('\n');
        io.printLine("ENTER READ");
        String s = io.readLine();
        io.printLine(s);
        //animation.interrupt();
    }

    public void startMenu() {
        io.showStartMenu();
    }

    public void createNewGame() {

    }

    public void loadSavedGame() {

    }

    public void runGame() {
        while (game.isRunning()) {
            //step 1 render view
            //step 2 get input
            //step 3 game.runTurn()
            //step 4 log
            //step 5 check winner -> setRunning()
        }
    }

    public void settlement() {

    }

    public void manual() {

    }

    public void exit() {

    }

    private static final class GameManagerHolder {
        private static final GameManager GAME_MANAGER = new GameManager();
    }
}
