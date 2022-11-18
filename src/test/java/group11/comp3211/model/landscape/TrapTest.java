package group11.comp3211.model.landscape;

import group11.comp3211.model.piece.Rat;
import group11.comp3211.view.Language;
import org.junit.Before;
import org.junit.Test;


public class TrapTest {
    Trap trap;

    @Before
    public void before() {
        trap = new Trap(2, 4, null);
    }

    @Test
    public void canLoad() {
        trap.canLoad(new Rat(1, 1, null));
    }

    @Test
    public void getSymbol() {
        trap.getSymbol(Language.ENGLISH);
        trap.getSymbol(Language.CHINESE_TRADITIONAL);
        trap.getSymbol(Language.CHINESE_TRADITIONAL);
    }
}