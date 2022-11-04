package group11.comp3211.model.landscape;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.piece.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LandTest {
    private PlayBoard playBoard;
    private Player player;

    @Before
    public void before() {
        playBoard = new PlayBoard();
        player = new Player("player");
    }

    /*
    Test if a land can load a particular piece.
     */
    @Test
    public void canLoadTest1() {
        Piece piece = new Cat(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load a cat! ", land.canLoad(piece));
    }

    @Test
    public void canLoadTest2() {
        Piece piece = new Dog(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load a dog! ", land.canLoad(piece));
    }

    @Test
    public void canLoadTest3() {
        Piece piece = new Elephant(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load an elephant! ", land.canLoad(piece));
    }

    @Test
    public void canLoadTest4() {
        Piece piece = new Leopard(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load a leopard! ", land.canLoad(piece));
    }

    @Test
    public void canLoadTest5() {
        Piece piece = new Lion(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load a lion! ", land.canLoad(piece));
    }

    @Test
    public void canLoadTest6() {
        Piece piece = new Rat(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load a rat! ", land.canLoad(piece));
    }

    @Test
    public void canLoadTest7() {
        Piece piece = new Tiger(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load a tiger! ", land.canLoad(piece));
    }

    @Test
    public void canLoadTest8() {
        Piece piece = new Wolf(1, 1, player);
        Land land = (Land) playBoard.get(1,1);
        Assert.assertTrue("Land cannot load a wolf! ", land.canLoad(piece));
    }

    /*
    Test if the attribute land.load is modified after calling land.load().
     */
    @Test
    public void loadTest1() throws LogicException {
        Piece piece = new Dog(2, 3, player);
        Land land = (Land) playBoard.get(2,3);
        land.load(piece);
        Assert.assertSame("land.load() has no effect to land.load! ", land.getLoad(), piece);
    }

    @Test
    public void loadTest2() throws LogicException {
        Piece piece = new Rat(2, 3, player);
        Land land = (Land) playBoard.get(2,3);
        land.load(piece);
        Assert.assertSame("land.load() has no effect to land.load! ", land.getLoad(), piece);
    }

    /*
    Test after loaded, whether a land can still load.
     */
    @Test
    public void LoadTest3() throws LogicException {
        Piece piece1 = new Leopard(3, 3, player);
        Piece piece2 = new Lion(3, 3, player);
        Land land = (Land) playBoard.get(3, 3);
        land.load(piece1);
        Assert.assertFalse("Land can still load after loaded! ", land.canLoad(piece2));
    }

    @Test
    public void LoadTest4() throws LogicException {
        Piece piece1 = new Tiger(3, 3, player);
        Piece piece2 = new Wolf(3, 3, player);
        Land land = (Land) playBoard.get(3, 3);
        land.load(piece1);
        Assert.assertFalse("Land can still load after loaded! ", land.canLoad(piece2));
    }

    /*
    Test whether a land can load more than one pieces.
     */
    @Test
    public void LoadTest5() throws LogicException {
        Piece piece1 = new Elephant(0, 5, player);
        Piece piece2 = new Cat(0, 5, player);
        Land land = (Land) playBoard.get(0, 5);
        land.load(piece1);
        try {
            land.load(piece2);
        } catch (LogicException e) {
            Assert.fail("Land can load more than one pieces! ");
        }
    }

    @Test
    public void LoadTest6() throws LogicException {
        Piece piece1 = new Wolf(0, 5, player);
        Piece piece2 = new Dog(0, 5, player);
        Land land = (Land) playBoard.get(0, 5);
        land.load(piece1);
        try {
            land.load(piece2);
        } catch (LogicException e) {
            Assert.fail("Land can load more than one pieces! ");
        }
    }
}
