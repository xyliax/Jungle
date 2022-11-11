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
 * Test for Piece: Elephant, Rank 8
 * Author: @LIUYunfei
 * Create: 2022/11/03
 * Review: @HANJiaming
 */
public class ElephantTest {
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
     * Test for Elephant moving on play board
     *    1. the elephant cannot move to out of the playboard
     * >> 2. the elephant cannot move more than one step
     * >> 3. the elephant is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     *    5. If there is an animal which ranks higher that it, elephant cannot move.
     *    6. Obstacle test： elephant cannot go across the obstacle
     *    7. If there is an animal which ranks lower that it, elephant can move
     */
    @SneakyThrows
    @Test
    public void moveTest1() {
        Elephant elephant = new Elephant(1, 2, player);
        playBoard.put(elephant);
        elephant.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == elephant && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Elephant moving on play board
     * >> 2. the elephant cannot move more than one step
     * >> 3. the elephant is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2() {
        Elephant elephant = new Elephant(1, 2, player);
        playBoard.put(elephant);
        elephant.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == elephant && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Elephant moving on play board
     * >> 1. the elephant cannot move to out of the playboard
     * >> 3. the elephant is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3() {
        Elephant elephant = new Elephant(8, 0, player);
        playBoard.put(elephant);
        elephant.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != elephant && before.getLoad() == elephant);
    }

    /**
     * Ignore the Exception
     * Test for Elephant moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 5. If there is an animal which ranks higher that it, elephant cannot move
     * **As Elephant is the highest rank, only rat can replace it.**
     */
    @SneakyThrows
    @Test
    public void moveTest4() {
        Elephant elephant = new Elephant(3, 0, player);
        playBoard.put(elephant);
        Rat rat = new Rat(2, 0, player);
        playBoard.put(rat);

        elephant.move(Direction.DOWN);
        Landscape elephantLocation = (Landscape) playBoard.get(3, 0);
        Landscape ratLocation = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(elephantLocation.getLoad() == elephant && ratLocation.getLoad() == rat);
    }

    /**
     * Ignore the Exception
     * Test for Elephant moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 6. Obstacle test： elephant cannot go across the obstacle
     */

    @SneakyThrows
    @Test
    public void moveTest5() {
        Elephant elephant = new Elephant(3, 0, player);
        playBoard.put(elephant);

        elephant.move(Direction.RIGHT);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == elephant && after.getLoad() != elephant);
    }

    /**
     * Ignore the Exception
     * Test for Elephant moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 7. If there is an animal which ranks lower that it, elephant can move
     */
    @SneakyThrows
    @Test
    public void moveTest6() {
        Elephant elephant = new Elephant(1, 2, player);
        playBoard.put(elephant);
        Lion lion = new Lion(2, 3, player);
        playBoard.put(lion);

        elephant.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == elephant && after.getLoad() != elephant);
    }

}