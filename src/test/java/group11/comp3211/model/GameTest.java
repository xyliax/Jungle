package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.common.exceptions.VoidObjectException;
import group11.comp3211.model.piece.Wolf;
import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class GameTest {
    Game game;

    @Before
    public void Before() {
        game = new Game();
        game.getPlayerX().setName("NameX");
        game.getPlayerY().setName("NameY");
        game.setRunning(true);
        game.setCurrentPlayer(new Random().nextInt() % 2 == 1 ? game.getPlayerX() : game.getPlayerY());
        game.setLanguage(Language.CHINESE_TRADITIONAL);
        Assert.assertTrue(game.isRunning());
    }

    @Test
    public void getFileList() {
        Assert.assertNotNull(Game.getFileList());
    }

    @Test
    public void selectPieceByKey() {
        try {
            game.selectPieceByKey('1');
        } catch (VoidObjectException e) {
            System.out.println("Get it.");
        }
    }

    @Test
    public void clearSelectStatus() {
        Wolf wolf = new Wolf(1, 2, null);
        wolf.setDirection(Direction.UP);
        game.setSelectedPiece(wolf);
        game.clearSelectStatus();
        Assert.assertNull(game.getSelectedPiece());
    }

    @Test
    public void runTurn() {
        Wolf wolf = new Wolf(1, 2, null);
        wolf.setSelected(true);
        wolf.setDirection(Direction.UP);
        game.setSelectedPiece(wolf);
        Assert.assertEquals(wolf.getDirection(), Direction.UP);
        try {
            game.runTurn();
        } catch (LogicException ignored) {
            System.out.println("Got it.");
        }
    }

    @Test
    public void saveToFile() throws IOException, ClassNotFoundException {
        game.clearSelectStatus();
        game.saveToFile("Demo.bin");
        Assert.assertNotNull(Game.loadFromFile("Demo.bin"));
    }


    @Test
    public void findWinner() {
        Assert.assertNull(game.findWinner());
    }

    @Test
    public void chores() {
        game.setRunning(true);
        Assert.assertTrue(game.isRunning());
        Player player = new Player(null);
        game.setCurrentPlayer(player);
        Assert.assertEquals(game.getCurrentPlayer(), player);
        Wolf wolf = new Wolf(1,6,null);
        game.setSelectedPiece(wolf);
        Assert.assertEquals(game.getSelectedPiece(), wolf);
        game.setLanguage(Language.ENGLISH);
        Assert.assertEquals(game.getLanguage(), Language.ENGLISH);
    }
}