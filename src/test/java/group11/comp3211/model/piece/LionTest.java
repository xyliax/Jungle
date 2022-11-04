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
 * Test for Piece: Lion, Rank 7
 * Author: @LIUYunfei
 * Create: 2022/11/03
 * Review: @HANJiaming
 */
public class LionTest {
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
     * Test for Lion moving on play board
     *    1. the lion cannot move to out of the playboard
     * >> 2. the lion cannot move more than one step
     * >> 3. the lion is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     *    5. If there is an animal which ranks higher that it, lion cannot move.
     *    6. Obstacle test： lion cannot go across the obstacle
     *    7. If there is an animal which ranks lower that it, lion can move
     */
    @SneakyThrows
    @Test
    public void moveTest1() {
        Lion lion = new Lion(1, 2, player);
        playBoard.put(lion);
        lion.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == lion && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Lion moving on play board
     * >> 2. the lion cannot move more than one step
     * >> 3. the lion is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2() {
        Lion lion = new Lion(1, 2, player);
        playBoard.put(lion);
        lion.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == lion && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Lion moving on play board
     * >> 1. the lion cannot move to out of the playboard
     * >> 3. the lion is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3() {
        Lion lion = new Lion(8, 0, player);
        playBoard.put(lion);
        lion.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != lion && before.getLoad() == lion);
    }

    /**
     * Ignore the Exception
     * Test for Lion moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 5. If there is an animal which ranks higher that it, lion cannot move
     */
    @SneakyThrows
    @Test
    public void moveTest4() {
        Lion lion = new Lion(3, 0, player);
        playBoard.put(lion);
        Elephant elephant = new Elephant(2, 0, player);
        playBoard.put(elephant);

        lion.move(Direction.DOWN);
        Landscape lionLocation = (Landscape) playBoard.get(3, 0);
        Landscape elephantLocation = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(lionLocation.getLoad() == lion && elephantLocation.getLoad() == elephant);
    }

    /**
     * Ignore the Exception
     * Test for Lion moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 6. Obstacle test： lion cannot go across the obstacle
     */

    @SneakyThrows
    @Test
    public void moveTest5() {
        Lion lion = new Lion(3, 0, player);
        playBoard.put(lion);

        lion.move(Direction.RIGHT);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == lion && after.getLoad() != lion);
    }

    /**
     * Ignore the Exception
     * Test for Lion moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 7. If there is an animal which ranks lower that it, lion can move
     */
    @SneakyThrows
    @Test
    public void moveTest6() {
        Lion lion = new Lion(1, 2, player);
        playBoard.put(lion);
        Rat rat = new Rat(2, 3, player);
        playBoard.put(rat);

        lion.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == lion && after.getLoad() != lion);
    }

}