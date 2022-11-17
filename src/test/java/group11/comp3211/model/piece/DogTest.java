package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DogTest {

    Dog dog;

    @Before
    public void before(){
        dog = new Dog(0,2,null);
    }

    @Test
    public void move() {
        dog.move(0,1);
        Assert.assertEquals(dog.getCol(),1);
        Assert.assertEquals(dog.getRow(),0);
    }

    @Test
    public void getSymbol() {
        Assert.assertNotNull(dog.getSymbol(Language.ENGLISH));
        Assert.assertNotNull(dog.getSymbol(Language.CHINESE_TRADITIONAL));
        Assert.assertNotNull(dog.getSymbol(Language.CHINESE_SIMPLE));
    }

    @Test
    public void testHashcode(){
        Assert.assertTrue(dog.hashCode() == dog.hashCode());
    }

}