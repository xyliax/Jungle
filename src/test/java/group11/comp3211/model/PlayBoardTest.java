package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.controller.GameManager;
import group11.comp3211.model.piece.*;
import group11.comp3211.view.Language;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;


public class PlayBoardTest {
    Game game;
    PlayBoard playBoard;

    Game game2;

    @Before
    public void Before(){
        game = new Game();
        game.getPlayerX().setName("NameX");
        game.getPlayerY().setName("NameY");
        game.setRunning(true);
        game.setCurrentPlayer(new Random().nextInt() % 2 == 1 ? game.getPlayerX() : game.getPlayerY());
        game.setLanguage(Language.CHINESE_TRADITIONAL);
        playBoard = game.getPlayboard();
    }

    @Test
    public void boot2(){
        File file = new File("/Users/hanjiaming/.game_file/userX-userY.game");
        GameManager.getInstance().debug(file);
        game2 = GameManager.getInstance().getGame();
    }

    @Test
    public void ratInRiver() {
        playBoard.ratInRiver(JungleType.RIVER_AREA_LEFT);
        playBoard.ratInRiver(JungleType.RIVER_AREA_RIGHT);
    }

    @Test
    public void findDestination() {
        Tiger tiger = new Tiger(4,4,null);
        tiger.setDirection(Direction.UP);
        try {
            playBoard.findDestination(tiger);
        } catch (LogicException e) {
        }

        tiger.setDirection(Direction.DOWN);
        try {
            playBoard.findDestination(tiger);
        } catch (LogicException e) {
        }

        tiger.setDirection(Direction.LEFT);
        try {
            playBoard.findDestination(tiger);
        } catch (LogicException e) {
        }

        tiger.setDirection(Direction.RIGHT);
        try {
            playBoard.findDestination(tiger);
        } catch (Exception e) {
        }

        try {
        Cat cat = new Cat(2,0,null);
        cat.setDirection(Direction.UP);
        playBoard.findDestination(cat);
        } catch (Exception e) {
        }

    }

    @Test
    public void doMove() {
        Elephant elephant = new Elephant(5,5,null);
        elephant.move(0,1);
        Rat rat = new Rat(5,6,null);
        try {
            playBoard.doMove(elephant);
        } catch (LogicException e) {
        }
    }

    @Test
    public void get() {
    }

    @Test
    public void gameRun() {

    }

    @Test
    public void initBoard() {
    }
}