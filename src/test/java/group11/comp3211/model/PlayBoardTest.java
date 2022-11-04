package group11.comp3211.model;

import group11.comp3211.model.landscape.Landscape;
import group11.comp3211.model.piece.Cat;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for playBoard
 * Author: @HANJiaming
 * Create: 2022/11/03
 * Review: @LIUYunfei
 */
public class PlayBoardTest {
    private PlayBoard playBoard;
    private Player player;

    /**
     * 1. Create the playboard
     * 2. Create a player
     */
    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player("player");
    }

    /**
     * Ignore the Exception
     *    1. allowed to move for x or y
     */
    @SneakyThrows
    @Test
    public void testCanMove1() {
        Cat cat = new Cat(2, 0, player);
        Assert.assertFalse(playBoard.canMove(cat,2,-1));
    }

    /**
     * Ignore the Exception
     *    3. a failed move does not work and will change nothing
     *    4. Obstacle test： cat cannot go across the obstacle
     */
    @SneakyThrows
    @Test
    public void testCanMove2() {
        Cat cat = new Cat(3, 0, player);
        Assert.assertFalse(playBoard.canMove(cat,3,1));
    }

    /**
     *   2. cannot move more than one step
     */
    @SneakyThrows
    @Test
    public void testCanMove3() {
        Cat cat = new Cat(3, 0, player);
        Assert.assertFalse(playBoard.canMove(cat,1,5));
    }

    /**
     * Ignore the Exception
     *   Test for Cat moving on play board
     */
    @SneakyThrows
    @Test
    public void testDoMove() {
        Cat cat = new Cat(1, 2, player);
        playBoard.put(cat);
        cat.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == cat && before.getLoad() == null);
    }

    /**
     * Test if a piece in on square
     */
    @Test
    public void testPut() {
        Cat cat = new Cat(1, 1, player);
        Landscape before = (Landscape) playBoard.get(1,1);
        Landscape after = (Landscape) playBoard.get(1, 1);
        playBoard.put(cat);
        Assert.assertTrue(before.getLoad() ==null && after.getLoad() == cat);
    }

    /**
     * Test if be able to get a piece
     */
    @Test
    public void testGet() {
        Cat cat = new Cat(1, 1, player);
        playBoard.put(cat);
        Assert.assertTrue(((Landscape)playBoard.get(1,1)).getLoad() == cat);
    }

}
