package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.common.exceptions.VoidObjectException;
import group11.comp3211.model.landscape.*;
import group11.comp3211.model.piece.*;
import group11.comp3211.view.Language;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static group11.comp3211.model.Direction.STAY;
import static group11.comp3211.view.Color.GREEN;
import static group11.comp3211.view.Color.RED;
import static group11.comp3211.view.Language.CHINESE_SIMPLE;

@Getter
@Setter
public final class Game implements Serializable {
    private static final File gamePath = new File("game_file");
    private final PlayBoard playboard;
    private final Player playerX;
    private final Player playerY;
    private final HashMap<String, Piece> keyPieceTable;
    private boolean running;
    private Player currentPlayer;
    private Piece selectedPiece;
    private Language language;

    public Game() {
        this.playboard = new PlayBoard();
        this.running = false;
        this.playerX = new Player(RED);
        this.playerY = new Player(GREEN);
        this.currentPlayer = null;
        this.selectedPiece = null;
        this.language = CHINESE_SIMPLE;
        keyPieceTable = new HashMap<>();
        initBoard();
        initKeyPieceTable();
    }

    public void selectPiece(char key) throws VoidObjectException {
        String ks = key + (currentPlayer == playerX ? "@X" : "@Y");
        Piece piece = keyPieceTable.get(ks);
        if (piece == null)
            throw new VoidObjectException();
        selectedPiece = piece;
    }

    public void clearSelectStatus() {
        if (selectedPiece != null) {
            if (selectedPiece.isSelected()) {
                if (selectedPiece.getDirection() != STAY)
                    selectedPiece.setDirection(STAY);
                selectedPiece.setSelected(false);
            }
            selectedPiece = null;
        }
    }

    public void runTurn() throws LogicException {

    }

    @SneakyThrows
    public void saveToFile(String fileName) {
        if (!gamePath.exists()) gamePath.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(gamePath, fileName));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    @SneakyThrows
    public Game loadFromFile(String fileName) {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        return (Game) objectInputStream.readObject();
    }

    @SneakyThrows
    private void initBoard() {
        playboard.put(new Den(0, 3, playerX));
        playboard.put(new Trap(0, 2, playerX));
        playboard.put(new Trap(0, 4, playerX));
        playboard.put(new Trap(1, 3, playerX));
        playboard.put(new Den(8, 3, playerY));
        playboard.put(new Trap(8, 2, playerX));
        playboard.put(new Trap(8, 4, playerX));
        playboard.put(new Trap(7, 3, playerX));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 7; col++) {
                if (playboard.get(row, col) != null) continue;
                if (row >= 3 && row <= 5) {
                    if (col == 0 || col == 3 || col == 6) playboard.put(new Land(row, col));
                    else playboard.put(new River(row, col));
                } else playboard.put(new Land(row, col));
            }
        }
        ArrayList<Piece> initPieces = new ArrayList<>();
        initPieces.add(new Elephant(2, 6, playerX));
        initPieces.add(new Lion(0, 0, playerX));
        initPieces.add(new Tiger(0, 6, playerX));
        initPieces.add(new Leopard(2, 2, playerX));
        initPieces.add(new Wolf(2, 4, playerX));
        initPieces.add(new Dog(1, 1, playerX));
        initPieces.add(new Cat(1, 5, playerX));
        initPieces.add(new Rat(2, 0, playerX));
        initPieces.add(new Elephant(6, 0, playerY));
        initPieces.add(new Lion(8, 6, playerY));
        initPieces.add(new Tiger(8, 0, playerY));
        initPieces.add(new Leopard(6, 4, playerY));
        initPieces.add(new Wolf(6, 2, playerY));
        initPieces.add(new Dog(7, 5, playerY));
        initPieces.add(new Cat(7, 1, playerY));
        initPieces.add(new Rat(6, 6, playerY));
        for (Piece piece : initPieces)
            playboard.get(piece.getRow(), piece.getCol()).load(piece);
    }

    private void initKeyPieceTable() {
        for (int row = 0; row < PlayBoard.ROW_NUM; row++) {
            for (int col = 0; col < PlayBoard.COL_NUM; col++) {
                Landscape landscape = (Landscape) playboard.get(row, col);
                Piece piece = (Piece) landscape.getLoad();
                if (piece == null) continue;
                String ks = piece.getRank() + (piece.getPlayer() == playerX ? "@X" : "@Y");
                keyPieceTable.put(ks, piece);
            }
        }
    }
}
