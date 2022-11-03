package group11.comp3211.model.piece;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.model.Direction;
import group11.comp3211.model.PlayBoard;
import group11.comp3211.model.Player;
import group11.comp3211.model.landscape.Landscape;
import org.junit.Before;
import org.junit.Test;

public class CatTest {
    private PlayBoard playBoard;
    private Player player;

    @Before
    public void init() {
        playBoard = new PlayBoard();
    }

    @Test
    public void move() throws LogicException {
        Cat cat = new Cat(1, 2, player);
        playBoard.put(cat);
        cat.move(Direction.LEFT);
        Landscape landscape = (Landscape) playBoard.get(1, 1);
        assert landscape.getLoad() == cat;
    }

}