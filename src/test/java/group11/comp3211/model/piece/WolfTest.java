package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WolfTest {

    Wolf wolf;

    @Before
    public void before(){
        wolf = new Wolf(0,2,null);
    }

    @Test
    public void die() {
        wolf.die();
        Assert.assertTrue(wolf.isDead());

    }

    @Test
    public void move() {
        wolf.move(0,1);
        Assert.assertEquals(wolf.getCol(),1);
        Assert.assertEquals(wolf.getRow(),0);
    }

    @Test
    public void getSymbol() {
        Assert.assertNotNull(wolf.getSymbol(Language.ENGLISH));
        Assert.assertNotNull(wolf.getSymbol(Language.CHINESE_TRADITIONAL));
        Assert.assertNotNull(wolf.getSymbol(Language.CHINESE_SIMPLE));
    }

    @Test
    public void testHashcode(){
        Assert.assertTrue(wolf.hashCode() == wolf.hashCode());
    }

}