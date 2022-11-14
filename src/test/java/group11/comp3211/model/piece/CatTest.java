package group11.comp3211.model.piece;

import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.landscape.Landscape;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Piece: Cat, Rank 2
 * Author: @HANJiaming
 * Create: 2022/11/03
 * Review: @LIUYunfei
 */
public class CatTest {
    private PlayBoard playBoard;
    private Player player;

    /**
     * 1. Create the playboard
     * 2. Create a player holding the piece
     */
    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player(null);
    }

    /**
     * Ignore the Exception
     * Test for Cat moving on play board
     *    1. the cat cannot move to out of the playboard
     * >> 2. the cat cannot move more than one step
     * >> 3. the cat is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     *    5. If there is an animal which ranks higher that it, cat cannot move.
     *    6. Obstacle test： cat cannot go across the obstacle
     *    7. If there is an animal which ranks lower that it, cat can move
     */
    @SneakyThrows
    @Test
    public void moveTest1() {
        Cat cat = new Cat(1, 2, player);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == cat && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Cat moving on play board
     * >> 2. the cat cannot move more than one step
     * >> 3. the cat is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2() {
        Cat cat = new Cat(1, 2, player);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == cat && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Cat moving on play board
     * >> 1. the cat cannot move to out of the playboard
     * >> 3. the cat is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3() {
        Cat cat = new Cat(8, 0, player);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != cat && before.getLoad() == cat);
    }

    /**
     * Ignore the Exception
     * Test for Cat moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 5. If there is an animal which ranks higher that it, cat cannot move
     */
    @SneakyThrows
    @Test
    public void moveTest4() {
        Cat cat = new Cat(3, 0, player);
        Tiger tiger = new Tiger(2, 0, player);
        Landscape catLocation = (Landscape) playBoard.get(3, 0);
        Landscape tigerLocation = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(catLocation.getLoad() == cat && tigerLocation.getLoad() == tiger);
    }

    /**
     * Ignore the Exception
     * Test for Cat moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 6. Obstacle test： cat cannot go across the obstacle
     */

    @SneakyThrows
    @Test
    public void moveTest5() {
        Cat cat = new Cat(3, 0, player);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == cat && after.getLoad() != cat);
    }

    /**
     * Ignore the Exception
     * Test for Cat moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 7. If there is an animal which ranks lower that it, cat can move
     */
    @SneakyThrows
    @Test
    public void moveTest6() {
        Cat cat = new Cat(1, 2, player);
        Rat rat = new Rat(2, 3, player);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == cat && after.getLoad() != cat);
    }

}