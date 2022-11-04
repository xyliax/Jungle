package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.piece.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DenTest {
    private PlayBoard playBoard;
    private Player player;

    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player("player");
    }

    /**
     * Test if a den can load a particular piece.
     */
    @Test
    public void canLoadTest1() {
        Piece piece = new Cat(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load a cat! ", den.canLoad(piece));
    }

    @Test
    public void canLoadTest2() {
        Piece piece = new Dog(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load a dog! ", den.canLoad(piece));
    }

    @Test
    public void canLoadTest3() {
        Piece piece = new Elephant(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load an elephant! ", den.canLoad(piece));
    }

    @Test
    public void canLoadTest4() {
        Piece piece = new Leopard(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load a leopard! ", den.canLoad(piece));
    }

    @Test
    public void canLoadTest5() {
        Piece piece = new Lion(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load a lion! ", den.canLoad(piece));
    }

    @Test
    public void canLoadTest6() {
        Piece piece = new Rat(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load a rat! ", den.canLoad(piece));
    }

    @Test
    public void canLoadTest7() {
        Piece piece = new Tiger(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load a tiger! ", den.canLoad(piece));
    }

    @Test
    public void canLoadTest8() {
        Piece piece = new Wolf(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        Assert.assertTrue("Den cannot load a wolf! ", den.canLoad(piece));
    }

    /**
     * Test if load is modified after calling den.load().
     *
     * @throws LogicException should not be throwed
     */
    @Test
    public void loadTest1() throws LogicException {
        Piece piece = new Dog(8, 3, player);
        Den den = (Den) playBoard.get(8, 3);
        den.load(piece);
        Assert.assertSame("den.load() has no effect to den.load! ", den.getLoad(), piece);
    }

    @Test
    public void loadTest2() throws LogicException {
        Piece piece = new Rat(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        den.load(piece);
        Assert.assertSame("den.load() has no effect to den.load! ", den.getLoad(), piece);
    }

    /**
     * Test after loaded, whether a den can still load.
     *
     * @throws LogicException should not be throwed
     */
    @Test
    public void LoadTest3() throws LogicException {
        Piece piece1 = new Leopard(0, 3, player);
        Piece piece2 = new Lion(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        den.load(piece1);
        Assert.assertFalse("Den can still load after loaded! ", den.canLoad(piece2));
    }

    @Test
    public void LoadTest4() throws LogicException {
        Piece piece1 = new Tiger(8, 3, player);
        Piece piece2 = new Wolf(8, 3, player);
        Den den = (Den) playBoard.get(8, 3);
        den.load(piece1);
        Assert.assertFalse("Den can still load after loaded! ", den.canLoad(piece2));
    }

    /**
     * Test whether a den can load more than one pieces.
     *
     * @throws LogicException should not be throwed
     */
    @Test
    public void LoadTest5() throws LogicException {
        Piece piece1 = new Elephant(0, 3, player);
        Piece piece2 = new Cat(0, 3, player);
        Den den = (Den) playBoard.get(0, 3);
        den.load(piece1);
        try {
            den.load(piece2);
        } catch (LogicException e) {
            Assert.fail("Den can load more than one pieces! ");
        }
    }

    @Test
    public void LoadTest6() throws LogicException {
        Piece piece1 = new Wolf(8, 3, player);
        Piece piece2 = new Dog(8, 3, player);
        Den den = (Den) playBoard.get(8, 3);
        den.load(piece1);
        try {
            den.load(piece2);
        } catch (LogicException e) {
            Assert.fail("Den can load more than one pieces! ");
        }
    }
}
