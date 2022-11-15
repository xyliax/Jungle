package group11.comp3211.model;

import group11.comp3211.common.exceptions.IllegalMovementException;
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
import java.util.Objects;

import static group11.comp3211.model.Direction.STAY;
import static group11.comp3211.view.Color.GREEN;
import static group11.comp3211.view.Color.RED;

@Getter
@Setter
public final class Game implements Serializable {
    private static final String gamePath = "game_file";
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
        this.language = null;
        keyPieceTable = new HashMap<>();
        initBoard();
        initKeyPieceTable();
    }

    public static String[] getFileList() {
        File ResDir = new File(Objects.requireNonNull(Game.class.getResource("/")).getFile());
        File gameFileDir = new File(ResDir, gamePath);
        if (!gameFileDir.exists()) return null;
        return gameFileDir.list((dir, name) -> name.endsWith(".game"));
    }

    @SneakyThrows
    public static Game loadFromFile(String fileName) {
        File ResDir = new File(Objects.requireNonNull(Game.class.getResource("/")).getFile());
        File gameFileDir = new File(ResDir, gamePath);
        FileInputStream fileInputStream = new FileInputStream(new File(gameFileDir, fileName));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (Game) objectInputStream.readObject();
    }

    public void selectPieceByKey(char key) throws VoidObjectException {
        String ks = key + (currentPlayer == playerX ? "@X" : "@Y");
        Piece piece = keyPieceTable.get(ks);
        if (piece == null)
            throw new VoidObjectException(String.format("Cannot find piece by '%s'", key));
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

    // selectedPiece; piece.direction
    public void runTurn() throws LogicException {
        if (!selectedPiece.isSelected())
            throw new VoidObjectException(String.format("%s is not confirmed!", selectedPiece.getSymbol(language)));
        if (selectedPiece.getDirection() == STAY)
            throw new IllegalMovementException(String.format("%s hasn't determine where to move!",
                    selectedPiece.getSymbol(language)));
        playboard.doMove(selectedPiece);
        clearSelectStatus();
        if (currentPlayer == playerX)
            currentPlayer = playerY;
        else currentPlayer = playerX;
    }

    @SneakyThrows
    public void saveToFile(String fileName) {
        File ResDir = new File(Objects.requireNonNull(Game.class.getResource("/")).getFile());
        File gameFileDir = new File(ResDir, gamePath);
        if (!gameFileDir.exists()) gameFileDir.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(gameFileDir, fileName));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
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
