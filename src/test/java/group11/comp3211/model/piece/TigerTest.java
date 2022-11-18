package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TigerTest {

    Tiger tiger;

    @Before
    public void before() {
        tiger = new Tiger(0, 2, null);
    }

    @Test
    public void move() {
        tiger.move(0, 1);
        Assert.assertEquals(tiger.getCol(), 1);
        Assert.assertEquals(tiger.getRow(), 0);
    }

    @Test
    public void getSymbol() {
        Assert.assertNotNull(tiger.getSymbol(Language.ENGLISH));
        Assert.assertNotNull(tiger.getSymbol(Language.CHINESE_TRADITIONAL));
        Assert.assertNotNull(tiger.getSymbol(Language.CHINESE_SIMPLE));
    }

    @Test
    public void testHashcode() {
        assertEquals(tiger.hashCode(), tiger.hashCode());
    }

}