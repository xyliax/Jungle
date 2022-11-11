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
 * Test for Piece: Rat, Rank 1
 * Author: @HANJiaming
 * Create: 2022/11/03
 * Review: @LIUYunfei, @PEIYuxing
 */
public class RatTest {

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
     * Test for Rat moving on play board
     * 1. the Rat cannot move to out of the playboard
     * >> 2. the Rat cannot move more than one step
     * >> 3. the Rat is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     * 5. If there is an animal which ranks higher that it, Rat cannot move.
     * 6. River test： Rat cannot go across the river
     * 7. If there is an animal which ranks lower that it, Rat can move
     */
    @SneakyThrows
    @Test
    public void moveTest1() {
        Rat rat = new Rat(1, 2, player);
        playBoard.put(rat);
        rat.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == rat && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Rat moving on play board
     * >> 2. the rat cannot move more than one step
     * >> 3. the rat is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2() {
        Rat rat = new Rat(1, 2, player);
        playBoard.put(rat);
        rat.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == rat && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Rat moving on play board
     * >> 1. the rat cannot move to out of the playboard
     * >> 3. the rat is allowed to move for x or y
     * >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3() {
        Rat rat = new Rat(8, 0, player);
        playBoard.put(rat);
        rat.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != rat && before.getLoad() == rat);
    }

    /**
     * Ignore the Exception
     * Test for Rat moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 5. If there is an animal which ranks higher that it, rat cannot move
     */
    @SneakyThrows
    @Test
    public void moveTest4() {
        Rat rat = new Rat(3, 0, player);
        playBoard.put(rat);
        Tiger tiger = new Tiger(2, 0, player);
        playBoard.put(tiger);

        rat.move(Direction.DOWN);
        Landscape ratLocation = (Landscape) playBoard.get(3, 0);
        Landscape tigerLocation = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(ratLocation.getLoad() == rat && tigerLocation.getLoad() == tiger);
    }

    /**
     * Ignore the Exception
     * Test for Rat moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 6. River test： rat can go across the river
     */

    @SneakyThrows
    @Test
    public void moveTest5() {
        Rat rat = new Rat(3, 0, player);
        playBoard.put(rat);

        rat.move(Direction.RIGHT);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == rat && after.getLoad() != rat);
    }

    /**
     * Ignore the Exception
     * Test for Rat moving on play board
     * >> 4. a failed move does not work and will change nothing
     * >> 7. If there is an animal which ranks lower that it, rat can move
     */
    @SneakyThrows
    @Test
    public void moveTest6() {
        Rat rat = new Rat(1, 2, player);
        playBoard.put(rat);
        Elephant elephant = new Elephant(2, 3, player);
        playBoard.put(elephant);

        rat.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == rat && after.getLoad() != rat);
    }


}