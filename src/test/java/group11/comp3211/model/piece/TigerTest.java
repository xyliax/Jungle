package group11.comp3211.model.piece;

import group11.comp3211.model.Direction;
import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.landscape.Landscape;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Piece: Tiger, Rank 6
 * Author: @LIUYunfei
 * Create: 2022/11/03
 * Review: @HANJiaming
 */
public class TigerTest {
    private PlayBoard playBoard;
    private Player player;

    /**
     * 1. Create the playboard
     * 2. Create a player holding the piece
     */
    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player("player");
    }

    /**
     * Ignore the Exception
     * Test for Tiger moving on play board
     *    1. the tiger cannot move to out of the playboard
     * >> 2. the tiger cannot move more than one step
     * >> 3. the tiger is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     *    5. If there is an animal which ranks higher that it, tiger cannot move.
     *    6. Obstacle test： tiger cannot go across the obstacle
     *    7. If there is an animal which ranks lower that it, tiger can move
     */
    @SneakyThrows
    @Test
    public void moveTest1() {
        Tiger tiger = new Tiger(1, 2, player);
        playBoard.put(tiger);
        tiger.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == tiger && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Tiger moving on play board
     * >> 2. the tiger cannot move more than one step
     * >> 3. the tiger is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2() {
        Tiger tiger = new Tiger(1, 2, player);
        playBoard.put(tiger);
        tiger.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == tiger && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Tiger moving on play board
     * >> 1. the tiger cannot move to out of the playboard
     * >> 3. the tiger is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3() {
        Tiger tiger = new Tiger(8, 0, player);
        playBoard.put(tiger);
        tiger.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != tiger && before.getLoad() == tiger);
    }

    /**
     * Ignore the Exception
     * Test for Tiger moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 5. If there is an animal which ranks higher that it, tiger cannot move
     */
    @SneakyThrows
    @Test
    public void moveTest4() {
        Tiger tiger = new Tiger(3, 0, player);
        playBoard.put(tiger);
        Elephant elephant = new Elephant(2, 0, player);
        playBoard.put(elephant);

        tiger.move(Direction.DOWN);
        Landscape tigerLocation = (Landscape) playBoard.get(3, 0);
        Landscape elephantLocation = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(tigerLocation.getLoad() == tiger && elephantLocation.getLoad() == elephant);
    }

    /**
     * Ignore the Exception
     * Test for Tiger moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 6. Obstacle test： tiger cannot go across the obstacle
     */

    @SneakyThrows
    @Test
    public void moveTest5() {
        Tiger tiger = new Tiger(3, 0, player);
        playBoard.put(tiger);

        tiger.move(Direction.RIGHT);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == tiger && after.getLoad() != tiger);
    }

    /**
     * Ignore the Exception
     * Test for Tiger moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 7. If there is an animal which ranks lower that it, tiger can move
     */
    @SneakyThrows
    @Test
    public void moveTest6() {
        Tiger tiger = new Tiger(1, 2, player);
        playBoard.put(tiger);
        Rat rat = new Rat(2, 3, player);
        playBoard.put(rat);

        tiger.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == tiger && after.getLoad() != tiger);
    }

}