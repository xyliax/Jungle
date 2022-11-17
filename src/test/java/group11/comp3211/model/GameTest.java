package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.common.exceptions.VoidObjectException;
import group11.comp3211.controller.GameManager;
import group11.comp3211.model.piece.Piece;
import group11.comp3211.model.piece.Wolf;
import group11.comp3211.view.Language;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

public class GameTest {
    Game game;


    @Before
    public void Before(){
        game = new Game();
        game.getPlayerX().setName("NameX");
        game.getPlayerY().setName("NameY");
        game.setRunning(true);
        game.setCurrentPlayer(new Random().nextInt() % 2 == 1 ? game.getPlayerX() : game.getPlayerY());
        game.setLanguage(Language.CHINESE_TRADITIONAL);
    }

    @Test
    public void getFileList() {

    }

    @Test
    public void loadFromFile() {
        Game.getFileList();
    }

    @Test
    public void selectPieceByKey() {
        try {
            game.selectPieceByKey('1');
        } catch (VoidObjectException e) {
        }
    }

    @Test
    public void clearSelectStatus() {
        Wolf wolf = new Wolf(1,2,null);
        wolf.setDirection(Direction.UP);
        game.setSelectedPiece(wolf);
        game.clearSelectStatus();
    }

    @Test
    public void runTurn() {
        Wolf wolf = new Wolf(1,2,null);
        wolf.setSelected(true);
        wolf.setDirection(Direction.UP);
        game.setSelectedPiece(wolf);
        try {
            game.runTurn();
        } catch (LogicException e) {
        }
    }

    @Test
    public void saveToFile() throws IOException, ClassNotFoundException {
        game.clearSelectStatus();
        game.saveToFile("Demo.bin");
        Game.loadFromFile("Demo.bin");
    }
}