package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.piece.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RiverTest {
    private PlayBoard playBoard;
    private Player player;

    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player("player");
    }

    /**
     * Test if river can load a particular piece.
     */
    @Test
    public void canLoadTest1() {
        Piece piece = new Cat(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertFalse("River can load a cat! ", river.canLoad(piece));
    }

    @Test
    public void canLoadTest2() {
        Piece piece = new Dog(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertFalse("River can load a dog! ", river.canLoad(piece));
    }

    @Test
    public void canLoadTest3() {
        Piece piece = new Elephant(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertFalse("River can load an elephant! ", river.canLoad(piece));
    }

    @Test
    public void canLoadTest4() {
        Piece piece = new Leopard(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertFalse("River can load a leopard! ", river.canLoad(piece));
    }

    @Test
    public void canLoadTest5() {
        Piece piece = new Lion(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertFalse("River can load a lion! ", river.canLoad(piece));
    }

    @Test
    public void canLoadTest6() {
        Piece piece = new Rat(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertTrue("River cannot load a rat! ", river.canLoad(piece));
    }

    @Test
    public void canLoadTest7() {
        Piece piece = new Tiger(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertFalse("River can load a tiger! ", river.canLoad(piece));
    }

    @Test
    public void canLoadTest8() {
        Piece piece = new Wolf(4, 5, player);
        River river = (River) playBoard.get(4, 5);
        Assert.assertFalse("River can load a wolf! ", river.canLoad(piece));
    }

    /**
     * Test if the attribute land.load is modified after calling land.load().
     *
     * @throws LogicException should not be throwed
     */
    @Test
    public void loadTest1() throws LogicException {
        Piece piece = new Rat(5, 4, player);
        River river = (River) playBoard.get(5, 4);
        river.load(piece);
        Assert.assertSame("river.load() has no effect to river.load! ", river.getLoad(), piece);
    }

    @Test
    public void loadTest2() throws LogicException {
        Piece piece = new Rat(5, 5, player);
        River river = (River) playBoard.get(5, 5);
        river.load(piece);
        Assert.assertSame("river.load() has no effect to river.load! ", river.getLoad(), piece);
    }

    /**
     * Test after loaded, whether a river can still load.
     *
     * @throws LogicException should not be throwed
     */
    @Test
    public void LoadTest3() throws LogicException {
        Piece piece1 = new Rat(3, 1, player);
        Piece piece2 = new Rat(3, 1, player);
        River river = (River) playBoard.get(3, 1);
        river.load(piece1);
        Assert.assertFalse("River can still load after loaded! ", river.canLoad(piece2));
    }

    /**
     * Test whether a river can load more than one pieces.
     *
     * @throws LogicException should not be throwed
     */
    @Test
    public void LoadTest4() throws LogicException {
        Piece piece1 = new Rat(4, 4, player);
        Piece piece2 = new Rat(4, 4, player);
        River river = (River) playBoard.get(4, 4);
        river.load(piece1);
        try {
            river.load(piece2);
        } catch (LogicException e) {
            Assert.fail("River can load more than one pieces! ");
        }
    }

}
