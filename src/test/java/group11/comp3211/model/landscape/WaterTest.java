package group11.comp3211.model.landscape;

import org.junit.Assert;
import org.junit.Test;

public class WaterTest {

    Water water = new Water(1, 3);
    Water water2 = new Water(1, 4);

    @Test
    public void canEqual() {
        water.equals(water2);
    }

    @Test
    public void testHashCode() {
        Assert.assertNotEquals(water.hashCode(), water2.hashCode());

    }
}