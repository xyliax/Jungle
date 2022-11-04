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
 * Test for Piece: Dog, Rank 3
 * Author: @HANJiaming
 * Create: 2022/11/03
 * Review: @LIUYunfei, @PEIYuxing
 */
public class DogTest {

    private PlayBoard playBoard;
    private Player player;

    /**
     * 1. Create the playboard
     * 2. Create a player helding the piece
     */
    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player("player");
    }

    /**
     * Ignore the Exception
     * Test for Dog moving on play board
     *     1. the dog cannot move to out of the playboard
     *  >> 2. the dog cannot move more than one step
     *  >> 3. the dog is allowed to move for x or y
     *  >> 4. a failed move does not work and will change nothing
     *     5. If there is an animal which ranks higher that it, dog cannot move.
     *     6. Obstacle test： dog cannot go accross the obstacle
     *     7. If there is an animal which ranks lower that it, dog can move
     */
    @SneakyThrows
    @Test
    public void moveTest1(){
        Dog dog = new Dog(1, 2, player);
        playBoard.put(dog);
        dog.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(1, 1);
        Assert.assertTrue(after.getLoad() == dog && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Dog moving on play board
     *  >> 2. the dog cannot move more than one step
     *  >> 3. the dog is allowed to move for x or y
     *  >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest2(){
        Dog dog = new Dog(1, 2, player);
        playBoard.put(dog);
        dog.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 2);
        Assert.assertTrue(after.getLoad() == dog && before.getLoad() == null);
    }

    /**
     * Ignore the Exception
     * Test for Dog moving on play board
     *  >> 1. the dog cannot move to out of the playboard
     *  >> 3. the dog is allowed to move for x or y
     *  >> 4. a failed move does not work and will change nothing
     */
    @SneakyThrows
    @Test
    public void moveTest3(){
        Dog dog = new Dog(8, 0, player);
        playBoard.put(dog);
        dog.move(Direction.LEFT);
        Landscape before = (Landscape) playBoard.get(8, 0);
        Landscape after = (Landscape) playBoard.get(8, 0);
        Assert.assertTrue(after.getLoad() != dog && before.getLoad() == dog);
    }

    /**
     * Ignore the Exception
     * Test for Dog moving on play board
     *  >> 4. a failed move does not work and will change nothing
     *  >> 5. If there is an animal which ranks higher that it, dog cannot move
     */
    @SneakyThrows
    @Test
    public void moveTest4(){
        Dog dog = new Dog(3, 0, player);
        playBoard.put(dog);
        Tiger tiger = new Tiger(2,0,player);
        playBoard.put(tiger);

        dog.move(Direction.DOWN);
        Landscape dogLodogion = (Landscape) playBoard.get(3, 0);
        Landscape tigerLodogion = (Landscape) playBoard.get(2, 0);
        Assert.assertTrue(dogLodogion.getLoad() == dog && tigerLodogion.getLoad() == tiger);
    }

    /**
     * Ignore the Exception
     * Test for Dog moving on play board
     *  >> 4. a failed move does not work and will change nothing
     *  >> 6. Obstacle test： dog cannot go accross the obstacle
     */

    @SneakyThrows
    @Test
    public void moveTest5(){
        Dog dog = new Dog(3, 0, player);
        playBoard.put(dog);

        dog.move(Direction.RIGHT);
        Landscape before = (Landscape) playBoard.get(3, 0);
        Landscape after = (Landscape) playBoard.get(3, 1);
        Assert.assertTrue(before.getLoad() == dog && after.getLoad() != dog);
    }

    /**
     * Ignore the Exception
     * Test for Dog moving on play board
     *  >> 4. a failed move does not work and will change nothing
     *  >> 7. If there is an animal which ranks lower that it, dog can move
     */
    @SneakyThrows
    @Test
    public void moveTest6(){
        Dog dog = new Dog(1, 2, player);
        playBoard.put(dog);
        Rat rat = new Rat(2,3,player);
        playBoard.put(rat);

        dog.move(Direction.UP);
        Landscape before = (Landscape) playBoard.get(1, 2);
        Landscape after = (Landscape) playBoard.get(2, 3);

        Assert.assertTrue(before.getLoad() == dog && after.getLoad() != dog);
    }
}