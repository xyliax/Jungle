package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.piece.*;
import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;


public class PlayBoardTest {
    Game game;
    PlayBoard playBoard;

    @Before
    public void Before() {
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
        Assert.assertFalse(playBoard.ratInRiver(JungleType.RIVER_AREA_LEFT));
        Assert.assertFalse(playBoard.ratInRiver(JungleType.RIVER_AREA_RIGHT));
    }

    @Test
    public void findDestination() {
        Tiger tiger = new Tiger(4, 4, null);
        tiger.setDirection(Direction.UP);
        try {
            playBoard.findDestination(tiger);
        } catch (LogicException ignored) {
        }

        tiger.setDirection(Direction.DOWN);
        try {
            playBoard.findDestination(tiger);
        } catch (LogicException ignored) {
        }

        tiger.setDirection(Direction.LEFT);
        try {
            playBoard.findDestination(tiger);
        } catch (LogicException ignored) {
        }

        tiger.setDirection(Direction.RIGHT);
        try {
            playBoard.findDestination(tiger);
        } catch (Exception ignored) {
        }

        try {
            Cat cat = new Cat(2, 0, null);
            cat.setDirection(Direction.UP);
            playBoard.findDestination(cat);
        } catch (Exception ignored) {
        }
        Assert.assertEquals(tiger.getRow(),4);
    }

    @Test
    public void doMove() {
        Elephant elephant = new Elephant(5, 5, null);
        elephant.move(0, 1);
        try {
            playBoard.doMove(elephant);
        } catch (LogicException ignored) {
            System.out.println("Got it");
        }
        Assert.assertEquals(elephant.getCol(),1);
    }

    @Test
    public void canCapture() {
        Player player1 = new Player(null);
        Player player2 = new Player(null);
        Elephant elephant = new Elephant(1, 2, player1);
        Rat rat = new Rat(2, 2, player2);
        Assert.assertFalse(playBoard.canCapture(elephant, rat));
        Assert.assertTrue(playBoard.canCapture(rat, elephant));
        Wolf wolf = new Wolf(1, 1, null);
        Assert.assertTrue(playBoard.canCapture(wolf, rat));
    }


}