package group11.comp3211.model.landscape;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DenTest {
    Den den;

    @Before
    public void Before() {
        den = new Den(1, 1, null);
    }

    @Test
    public void testGetSymbol() {
        Assert.assertEquals(den.getSymbol(Language.CHINESE_SIMPLE), "穴");
        Assert.assertEquals(den.getSymbol(Language.ENGLISH), "DN");
        Assert.assertEquals(den.getSymbol(Language.CHINESE_TRADITIONAL), "穴");
    }

    @Test
    public void testGetPlayer() {
        Assert.assertNull(den.getPlayer());
    }

    @Test
    public void testHashcode() {
        System.out.println(den.hashCode() == den.hashCode());
    }
}