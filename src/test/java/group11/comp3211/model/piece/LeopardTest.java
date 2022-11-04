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
 * Test for Piece: Leopard, Rank 5
 * Author: @LIUYunfei
 * Create: 2022/11/03
 * Review: @HANJiaming
 */
public class LeopardTest {
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
     * Test for Leopard moving on play board
     *    1. the leopard cannot move to out of the playboard
     * >> 2. the leopard cannot move more than one step
     * >> 3. the leopard is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     *    5. If there is an animal which ranks higher that it, leopard cannot move.
     *    6. Obstacle test： leopard cannot go across the obstacle
     *    7. If there is an animal which ranks lower that it, leopard can move
     */
    @SneakyThrows
    @Test
    public void moveTest1() {
        Leopard leopard = new Leopard(1, 2, player);
        playBoard.put(leopard);
        leopard.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == leopard && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Leopard moving on play board
     * >> 2. the leopard cannot move more than one step
     * >> 3. the leopard is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2() {
        Leopard leopard = new Leopard(1, 2, player);
        playBoard.put(leopard);
        leopard.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == leopard && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Leopard moving on play board
     * >> 1. the leopard cannot move to out of the playboard
     * >> 3. the leopard is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3() {
        Leopard leopard = new Leopard(8, 0, player);
        playBoard.put(leopard);
        leopard.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != leopard && before.getLoad() == leopard);
    }

    /**
     * Ignore the Exception
     * Test for Leopard moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 5. If there is an animal which ranks higher that it, leopard cannot move
     */
    @SneakyThrows
    @Test
    public void moveTest4() {
        Leopard leopard = new Leopard(3, 0, player);
        playBoard.put(leopard);
        Tiger tiger = new Tiger(2, 0, player);
        playBoard.put(tiger);

        leopard.move(Direction.DOWN);
        Landscape leopardLocation = (Landscape) playBoard.get(3, 0);
        Landscape tigerLocation = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(leopardLocation.getLoad() == leopard && tigerLocation.getLoad() == tiger);
    }

    /**
     * Ignore the Exception
     * Test for Leopard moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 6. Obstacle test： leopard cannot go across the obstacle
     */

    @SneakyThrows
    @Test
    public void moveTest5() {
        Leopard leopard = new Leopard(3, 0, player);
        playBoard.put(leopard);

        leopard.move(Direction.RIGHT);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == leopard && after.getLoad() != leopard);
    }

    /**
     * Ignore the Exception
     * Test for Leopard moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 7. If there is an animal which ranks lower that it, leopard can move
     */
    @SneakyThrows
    @Test
    public void moveTest6() {
        Leopard leopard = new Leopard(1, 2, player);
        playBoard.put(leopard);
        Rat rat = new Rat(2, 3, player);
        playBoard.put(rat);

        leopard.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == leopard && after.getLoad() != leopard);
    }

}