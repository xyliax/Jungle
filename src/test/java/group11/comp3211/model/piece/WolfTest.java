package group11.comp3211.model.piece;

import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.landscape.Landscape;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Piece: Wolf, Rank 4
 * Author: @HANJiaming
 * Create: 2022/11/03
 * Review: @LIUYunfei
 */
public class WolfTest {

    private PlayBoard playBoard;
    private Player player;

    /**
     * 1. Create the playboard
     * 2. Create a player helding the piece
     */
    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player(null);
    }

    /**
     * Ignore the Exception
     * Test for Wolf moving on play board
     * 1. the wolf cannot move to out of the playboard
     * >> 2. the wolf cannot move more than one step
     * >> 3. the wolf is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     * 5. If there is an animal which ranks higher that it, wolf cannot move.
     * 6. Obstacle test： wolf cannot go across the obstacle
     * 7. If there is an animal which ranks lower that it, wolf can move
     */
    @SneakyThrows
    @Test
    public void moveTest1() {
        Wolf wolf = new Wolf(1, 2, player);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == wolf && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Wolf moving on play board
     * >> 2. the wolf cannot move more than one step
     * >> 3. the wolf is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2() {
        Wolf wolf = new Wolf(1, 2, player);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == wolf && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Wolf moving on play board
     * >> 1. the wolf cannot move to out of the playboard
     * >> 3. the wolf is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3() {
        Wolf wolf = new Wolf(8, 0, player);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != wolf && before.getLoad() == wolf);
    }

    /**
     * Ignore the Exception
     * Test for Wolf moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 5. If there is an animal which ranks higher that it, wolf cannot move
     */
    @SneakyThrows
    @Test
    public void moveTest4() {
        Wolf wolf = new Wolf(3, 0, player);
        Tiger tiger = new Tiger(2, 0, player);
        Landscape wolfLowolfion = (Landscape) playBoard.get(3, 0);
        Landscape tigerLowolfion = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(wolfLowolfion.getLoad() == wolf && tigerLowolfion.getLoad() == tiger);
    }

    /**
     * Ignore the Exception
     * Test for Wolf moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 6. Obstacle test： wolf cannot go across the obstacle
     */

    @SneakyThrows
    @Test
    public void moveTest5() {
        Wolf wolf = new Wolf(3, 0, player);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == wolf && after.getLoad() != wolf);
    }

    /**
     * Ignore the Exception
     * Test for Wolf moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 7. If there is an animal which ranks lower that it, wolf can move
     */
    @SneakyThrows
    @Test
    public void moveTest6() {
        Wolf wolf = new Wolf(1, 2, player);
        Rat rat = new Rat(2, 3, player);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == wolf && after.getLoad() != wolf);
    }
}