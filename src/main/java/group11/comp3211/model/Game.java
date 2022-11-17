package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import group11.comp3211.common.exceptions.VoidObjectException;
import group11.comp3211.model.landscape.Landscape;
import group11.comp3211.model.piece.Piece;
import group11.comp3211.view.Language;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.*;
import java.util.HashMap;

import static group11.comp3211.model.Direction.STAY;
import static group11.comp3211.view.Color.GREEN;
import static group11.comp3211.view.Color.RED;

@Getter
@Setter
public final class Game implements Serializable {
    private static final String gamePath = ".game_file";
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
        playboard.initBoard(playerX, playerY);
        initKeyPieceTable();
    }

    public static String[] getFileList() {
        File ResDir = new File(System.getenv("HOME"));
        File gameFileDir = new File(ResDir, gamePath);
        if (!gameFileDir.exists()) return null;
        return gameFileDir.list((dir, name) -> name.endsWith(".game"));
    }

    public static Game loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        File ResDir = new File(System.getenv("HOME"));
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
        if (!(playboard.getAlivePieces().contains(piece)))
            throw new VoidObjectException(String.format("%s has been captured!", piece.getSymbol(language)));
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
        if (!selectedPiece.isSelected())
            throw new VoidObjectException(String.format("%s is not confirmed!", selectedPiece.getSymbol(language)));
        if (selectedPiece.getDirection() == STAY)
            throw new LogicException(String.format("%s has not determine moving direction!",
                    selectedPiece.getSymbol(language)));
        playboard.doMove(selectedPiece);
        clearSelectStatus();
        if (currentPlayer == playerX)
            currentPlayer = playerY;
        else currentPlayer = playerX;
    }

    public Player findWinner() {
        if (playboard.get(0, 3).getLoad() != null)
            return playerY;
        else if(playboard.get(8, 3).getLoad() != null)
            return playerX;
        else {
            boolean aliveX = false, aliveY = false;
            for(Piece alivePiece: playboard.getAlivePieces()) {
                if(alivePiece.getPlayer() == playerX)
                    aliveX = true;
                else
                    aliveY = true;
            }
            if(!aliveX) return playerY;
            else if(!aliveY) return playerX;
        }
        return null;
    }

    @SneakyThrows
    public void saveToFile(String fileName) {
        File ResDir = new File(System.getenv("HOME"));
        File gameFileDir = new File(ResDir, gamePath);
        if (!gameFileDir.exists()) gameFileDir.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(gameFileDir, fileName));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
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
