package group11.comp3211.model;

import group11.comp3211.view.Color;
import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

public class PlayerTest {
    Player player;

    @Before
    public void before(){
        player = new Player(Color.RED);
    }


    @Test
    public void testToString() {
        player.toString();
    }

    @Test
    public void getName() {
        player.setName("NN");
        player.getName();
    }

    @Test
    public void getColor() {
        player.getColor();
    }


    @Test
    public void setColor() {
        player.setColor(Color.RED);
    }

    @Test
    public void testEquals() {
        Player player1 = new Player(C);
        player.equals(player);
    }

    @Test
    public void testHashCode() {
    }
}