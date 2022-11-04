package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.piece.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrapTest {
    private PlayBoard playBoard;
    private Player player;

    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player("player");
    }

    /*
    Test if a trap can load a particular piece.
     */
    @Test
    public void canLoadTest1() {
        Piece piece = new Cat(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load a cat! ", trap.canLoad(piece));
    }

    @Test
    public void canLoadTest2() {
        Piece piece = new Dog(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load a dog! ", trap.canLoad(piece));
    }

    @Test
    public void canLoadTest3() {
        Piece piece = new Elephant(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load an elephant! ", trap.canLoad(piece));
    }

    @Test
    public void canLoadTest4() {
        Piece piece = new Leopard(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load a leopard! ", trap.canLoad(piece));
    }

    @Test
    public void canLoadTest5() {
        Piece piece = new Lion(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load a lion! ", trap.canLoad(piece));
    }

    @Test
    public void canLoadTest6() {
        Piece piece = new Rat(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load a rat! ", trap.canLoad(piece));
    }

    @Test
    public void canLoadTest7() {
        Piece piece = new Tiger(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load a tiger! ", trap.canLoad(piece));
    }

    @Test
    public void canLoadTest8() {
        Piece piece = new Wolf(7, 3, player);
        Trap trap = (Trap) playBoard.get(7,3);
        Assert.assertTrue("Trap cannot load a wolf! ", trap.canLoad(piece));
    }

    /*
    Test if the attribute trap.load is modified after calling trap.load().
     */
    @Test
    public void loadTest1() throws LogicException {
        Piece piece = new Dog(8, 4, player);
        Trap trap = (Trap) playBoard.get(8,4);
        trap.load(piece);
        Assert.assertSame("trap.load() has no effect to trap.load! ", trap.getLoad(), piece);
    }

    @Test
    public void loadTest2() throws LogicException {
        Piece piece = new Rat(1, 3, player);
        Trap trap = (Trap) playBoard.get(1,3);
        trap.load(piece);
        Assert.assertSame("trap.load() has no effect to trap.load! ", trap.getLoad(), piece);
    }

    /*
    Test after loaded, whether a trap can still load.
     */
    @Test
    public void LoadTest3() throws LogicException {
        Piece piece1 = new Leopard(0, 4, player);
        Piece piece2 = new Lion(0, 4, player);
        Trap trap = (Trap) playBoard.get(0,4);
        trap.load(piece1);
        Assert.assertFalse("Trap can still load after loaded! ", trap.canLoad(piece2));
    }

    @Test
    public void LoadTest4() throws LogicException {
        Piece piece1 = new Tiger(0, 2, player);
        Piece piece2 = new Wolf(0, 2, player);
        Trap trap = (Trap) playBoard.get(0,2);
        trap.load(piece1);
        Assert.assertFalse("Trap can still load after loaded! ", trap.canLoad(piece2));
    }

    /*
    Test whether a trap can load more than one pieces.
     */
    @Test
    public void LoadTest5() throws LogicException {
        Piece piece1 = new Elephant(8, 2, player);
        Piece piece2 = new Cat(8, 2, player);
        Trap trap = (Trap) playBoard.get(8,2);
        trap.load(piece1);
        try {
            trap.load(piece2);
        } catch (LogicException e) {
            Assert.fail("Trap can load more than one pieces! ");
        }
    }

    @Test
    public void LoadTest6() throws LogicException {
        Piece piece1 = new Wolf(1, 3, player);
        Piece piece2 = new Dog(1, 3, player);
        Trap trap = (Trap) playBoard.get(1,3);
        trap.load(piece1);
        try {
            trap.load(piece2);
        } catch (LogicException e) {
            Assert.fail("Trap can load more than one pieces! ");
        }
    }
}
