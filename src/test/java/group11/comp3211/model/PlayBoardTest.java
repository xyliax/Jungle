package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.piece.Cat;
import group11.comp3211.model.piece.Rat;
import group11.comp3211.model.piece.Tiger;
import group11.comp3211.model.piece.Wolf;
import group11.comp3211.view.Language;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class PlayBoardTest {
    Game game;
    PlayBoard playBoard;

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
    public void ratInRiver() {
        playBoard.ratInRiver(JungleType.RIVER_AREA_LEFT);
        playBoard.ratInRiver(JungleType.RIVER_AREA_RIGHT);
    }

    @Test
    public void findDestination() throws LogicException {
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
        Wolf wolf = new Wolf(1,1,null);
        wolf.move(0,1);
        Rat rat = new Rat(1,2,null);
        try {
            playBoard.doMove(wolf);
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