package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElephantTest {

    Elephant elephant;

    @Before
    public void before(){
        elephant = new Elephant(0,2,null);
    }

    @Test
    public void move() {
        elephant.move(0,1);
        Assert.assertEquals(elephant.getCol(),1);
        Assert.assertEquals(elephant.getRow(),0);
    }

    @Test
    public void getSymbol() {
        Assert.assertNotNull(elephant.getSymbol(Language.ENGLISH));
        Assert.assertNotNull(elephant.getSymbol(Language.CHINESE_TRADITIONAL));
        Assert.assertNotNull(elephant.getSymbol(Language.CHINESE_SIMPLE));
    }

    @Test
    public void testHashcode(){
        assertEquals(elephant.hashCode(), elephant.hashCode());
    }

}