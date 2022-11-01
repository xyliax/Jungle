package group11.comp3211.controller;

import group11.comp3211.model.Game;
import group11.comp3211.view.JungleIO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class GameManager {
    private Game game;
    private JungleIO jungleIO;
    private Parser parser;
    private Thread welcomeAnimation;

    private GameManager() {
        setGame(null);
        setJungleIO(JungleIO.getInstance());
        setParser(Parser.getInstance());
    }

    public static GameManager getInstance() {
        return GameManagerHolder.GAME_MANAGER;
    }

    public void boot() {
        //Thread animation = jungleIO.showWelcomeAnimation();
        String input = jungleIO.readNoPrompt();
        while (input.length() != 0) {
            input = jungleIO.readNoPrompt();
        }
        jungleIO.printLine("ENTER READ");
        //animation.interrupt();
    }

    public void startMenu() {
        jungleIO.showStartMenu();
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
