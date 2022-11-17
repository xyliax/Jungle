package group11.comp3211.model;

import group11.comp3211.common.exceptions.*;
import group11.comp3211.model.landscape.*;
import group11.comp3211.model.piece.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.ArrayList;

import static group11.comp3211.model.JungleType.*;

/**
 * Provide a play board and control the pieces and loaders on it to simulate a game.
 */
@Getter
public final class PlayBoard implements Serializable {
    /**
     * the row number of the board
     */
    public static final int ROW_NUM = 9;
    /**
     * the column number of the board
     */
    public static final int COL_NUM = 7;
    private final ArrayList<Piece> alivePieces;
    private final Loader[][] board;

    /**
     * constructor for an empty board (without any pieces)
     */
    public PlayBoard() {
        this.alivePieces = new ArrayList<>();
        this.board = new Loader[ROW_NUM][COL_NUM];
    }

    /**
     * get the loader by coordinate
     * @param row row number
     * @param col column number
     * @return a loader
     */
    public Loader get(int row, int col) {
        return board[row][col];
    }

    private Loader getPieceLoader(Piece piece) {
        return this.get(piece.getRow(), (piece.getCol()));
    }

    /**
     * Check whether a piece can capture another piece.
     * @param capturer the piece to capture
     * @param capturee the piece to be captured
     * @return can or cannot
     */
    public boolean canCapture(Piece capturer, Piece capturee) {
        if (capturer.getPlayer() == capturee.getPlayer()) return false;
        if (getPieceLoader(capturee).getType() == TRAP) return true;
        switch (capturer.getType()) {
            case ELEPHANT -> {
                return capturee.getType() != RAT;
            }
            case RAT -> {
                if (getPieceLoader(capturer).getType() == WATER) return getPieceLoader(capturee).getType() == WATER;
                else return capturee.getType() == RAT || capturee.getType() == ELEPHANT;
            }
            default -> {
                return capturee.getRank() <= capturer.getRank();
            }
        }
    }

    /**
     * Check whether there is a rat in a certain river area.
     * @param jungleType RIVER_AREA_LEFT or RIVER_AREA_RIGHT
     * @return exists or not
     */
    public boolean ratInRiver(JungleType jungleType) {
        switch (jungleType) {
            case RIVER_AREA_LEFT -> {
                for (int i = 3; i <= 5; ++i) {
                    for (int j = 1; j <= 2; ++j)
                        if (get(i, j).getLoad() != null) return true;
                }
            }
            case RIVER_AREA_RIGHT -> {
                for (int i = 3; i <= 5; ++i) {
                    for (int j = 4; j <= 5; ++j)
                        if (get(i, j).getLoad() != null) return true;
                }
            }
        }
        return false;
    }

    /**
     * Find the destination by a movable and its direction disregarding any other pieces
     * @param movable the piece
     * @return the destination coordinate
     * @throws LogicException illegal movement
     */
    public int[] findDestination(Movable movable) throws LogicException {
        int row = movable.getRow();
        int col = movable.getCol();
        switch (movable.getDirection()) {
            case UP -> --row;
            case DOWN -> ++row;
            case LEFT -> --col;
            case RIGHT -> ++col;
        }
        if (!(row >= 0 && row < ROW_NUM && col >= 0 && col < COL_NUM)) throw new OutBoardException(movable);
        if ((movable.getType() == LION || movable.getType() == TIGER) && get(row, col).getType() == WATER) {
            if (ratInRiver(((Water) get(row, col)).getArea()))
                throw new JumpingException(movable, ((Water) get(row, col)).getArea());
            else {
                switch (movable.getDirection()) {
                    case UP -> row -= 3;
                    case DOWN -> row += 3;
                    case LEFT -> col -= 2;
                    case RIGHT -> col += 2;
                }
            }
        }
        if (!get(row, col).canLoad(movable)) throw new NotLoadableException(get(row, col), movable);
        else if ((get(row, col).getType() == DEN && ((Den) get(row, col)).getPlayer() == ((Piece) movable).getPlayer()))
            throw new LogicException("Piece cannot step on DEN of the same side!");
        return new int[]{row, col};
    }

    /**
     * Do a whole process of move.
     * @param movable the piece to move with a direction
     * @throws LogicException illegal movement
     */
    public void doMove(Movable movable) throws LogicException {
        Piece piece = (Piece) movable;
        int[] dest = findDestination(movable);
        Piece target = (Piece) get(dest[0], dest[1]).getLoad();
        if (target != null && !canCapture(piece, target))
            throw new IllegalCaptureException(movable, target);
        if (target != null) {
            alivePieces.remove(target);
            get(dest[0], dest[1]).setLoad(null);
        }
        getPieceLoader(piece).setLoad(null);
        piece.move(dest[0], dest[1]);
        get(dest[0], dest[1]).setLoad(movable);
    }

    private void put(Loader loader) {
        Landscape landscape;
        if (loader instanceof Landscape) landscape = (Landscape) loader;
        else throw new IllegalArgumentException();
        board[landscape.getRow()][landscape.getCol()] = loader;
    }

    /**
     * Initialize the board by adding pieces on it.
     * @param playerX upper player
     * @param playerY lower player
     */
    @SneakyThrows
    public void initBoard(Player playerX, Player playerY) {
        put(new Den(0, 3, playerX));
        put(new Trap(0, 2, playerX));
        put(new Trap(0, 4, playerX));
        put(new Trap(1, 3, playerX));
        put(new Den(8, 3, playerY));
        put(new Trap(8, 2, playerY));
        put(new Trap(8, 4, playerY));
        put(new Trap(7, 3, playerY));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 7; col++) {
                if (get(row, col) != null) continue;
                if (row >= 3 && row <= 5) {
                    if (col == 0 || col == 3 || col == 6) put(new Land(row, col));
                    else put(new Water(row, col));
                } else put(new Land(row, col));
            }
        }
        alivePieces.add(new Elephant(2, 6, playerX));
        alivePieces.add(new Lion(0, 0, playerX));
        alivePieces.add(new Tiger(0, 6, playerX));
        alivePieces.add(new Leopard(2, 2, playerX));
        alivePieces.add(new Wolf(2, 4, playerX));
        alivePieces.add(new Dog(1, 1, playerX));
        alivePieces.add(new Cat(1, 5, playerX));
        alivePieces.add(new Rat(2, 0, playerX));
        alivePieces.add(new Elephant(6, 0, playerY));
        alivePieces.add(new Lion(8, 6, playerY));
        alivePieces.add(new Tiger(8, 0, playerY));
        alivePieces.add(new Leopard(6, 4, playerY));
        alivePieces.add(new Wolf(6, 2, playerY));
        alivePieces.add(new Dog(7, 5, playerY));
        alivePieces.add(new Cat(7, 1, playerY));
        alivePieces.add(new Rat(6, 6, playerY));

        for (Piece piece : alivePieces) get(piece.getRow(), piece.getCol()).setLoad(piece);
    }
}



