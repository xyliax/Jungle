package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeopardTest {

    Leopard leopard;

    @Before
    public void before() {
        leopard = new Leopard(0, 2, null);
    }

    @Test
    public void move() {
        leopard.move(0, 1);
        Assert.assertEquals(leopard.getCol(), 1);
        Assert.assertEquals(leopard.getRow(), 0);
    }

    @Test
    public void getSymbol() {
        Assert.assertNotNull(leopard.getSymbol(Language.ENGLISH));
        Assert.assertNotNull(leopard.getSymbol(Language.CHINESE_TRADITIONAL));
        Assert.assertNotNull(leopard.getSymbol(Language.CHINESE_SIMPLE));
    }

    @Test
    public void testHashcode() {
        assertEquals(leopard.hashCode(), leopard.hashCode());
    }

}