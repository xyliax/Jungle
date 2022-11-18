package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LionTest {

    Lion lion;

    @Before
    public void before() {
        lion = new Lion(0, 2, null);
    }

    @Test
    public void move() {
        lion.move(0, 1);
        Assert.assertEquals(lion.getCol(), 1);
        Assert.assertEquals(lion.getRow(), 0);
    }

    @Test
    public void getSymbol() {
        Assert.assertNotNull(lion.getSymbol(Language.ENGLISH));
        Assert.assertNotNull(lion.getSymbol(Language.CHINESE_TRADITIONAL));
        Assert.assertNotNull(lion.getSymbol(Language.CHINESE_SIMPLE));
    }

    @Test
    public void testHashcode() {
        assertEquals(lion.hashCode(), lion.hashCode());
    }

}