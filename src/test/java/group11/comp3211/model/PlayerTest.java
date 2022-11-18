package group11.comp3211.model;

import group11.comp3211.view.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player player;

    @Before
    public void before() {
        player = new Player(Color.RED);
    }


    @Test
    public void testToString() {
        System.out.println(player.toString());
    }

    @Test
    public void getName() {
        player.setName("NN");
        Assert.assertSame("NN", player.getName());
    }

    @Test
    public void getColor() {
        player.setColor(Color.RED);
        Assert.assertEquals(player.getColor(), Color.RED);
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(player, player);
    }

}