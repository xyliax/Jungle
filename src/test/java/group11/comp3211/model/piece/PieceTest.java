package group11.comp3211.model.piece;

import group11.comp3211.view.Language;
import org.junit.Before;
import org.junit.Test;

public class PieceTest {

    Rat rat;

    @Before
    public void Before() {
        rat = new Rat(1, 1, null);
    }

    @Test
    public void getSymbol() {
        rat.getSymbol(Language.ENGLISH);
        rat.getSymbol(Language.CHINESE_TRADITIONAL);
        rat.getSymbol(Language.CHINESE_SIMPLE);
    }

    @Test
    public void move() {
        rat.move(3, 5);
    }

    @Test
    public void getRank() {
        rat.getRank();
    }

    @Test
    public void getRow() {
        rat.getRow();
    }

    @Test
    public void getCol() {
        rat.getCol();
    }

    @Test
    public void isSelected() {
        rat.isSelected();
    }

    @Test
    public void getDirection() {
        rat.getDirection();
    }

    @Test
    public void getPlayer() {
        rat.getPlayer();
    }

    @Test
    public void getType() {
        rat.getPlayer();
    }

    @Test
    public void getJungleType() {
        rat.getType();
    }
}

