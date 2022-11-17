package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CatTest {

    Cat cat;

    @Before
    public void before(){
        cat = new Cat(0,2,null);
    }

    @Test
    public void die() {
        cat.die();
        Assert.assertTrue(cat.isDead());

    }

    @Test
    public void move() {
        cat.move(0,1);
        Assert.assertEquals(cat.getCol(),1);
        Assert.assertEquals(cat.getRow(),0);
    }

    @Test
    public void getSymbol() {
        Assert.assertNotNull(cat.getSymbol(Language.ENGLISH));
        Assert.assertNotNull(cat.getSymbol(Language.CHINESE_TRADITIONAL));
        Assert.assertNotNull(cat.getSymbol(Language.CHINESE_SIMPLE));
    }

    @Test
    public void testHashcode(){
        Assert.assertTrue(cat.hashCode() == cat.hashCode());
    }

}