package group11.comp3211.model.landscape;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LandTest{

    Land land;

    @Before
    public void Before(){
        land = new Land(2,4);
    }

    @Test
    public void testTestEquals() {
        Assert.assertEquals(land, land);
    }

    @Test
    public void testTestHashCode() {
        Assert.assertEquals(land.hashCode(), land.hashCode());
    }

    @Test
    public void testTestToString() {
        land.getCol();
    }

    @Test
    public void getRow() {
    }

    @Test
    public void getCol() {
    }

    @Test
    public void getLoad() {
    }

    @Test
    public void getAllowed() {
    }

    @Test
    public void getType() {
    }

    @Test
    public void setRow() {
    }

    @Test
    public void setCol() {
    }

    @Test
    public void setLoad() {
    }

    @Test
    public void setType() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void testEquals() {
    }


}