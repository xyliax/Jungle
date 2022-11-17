package group11.comp3211.model;

import group11.comp3211.common.exceptions.*;
import group11.comp3211.model.landscape.*;
import group11.comp3211.model.piece.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.ArrayList;

import static group11.comp3211.model.JungleType.*;

@Getter
public final class PlayBoard implements Serializable {
    public static final int ROW_NUM = 9;
    public static final int COL_NUM = 7;
    private final ArrayList<Piece> alivePieces;
    private final Loader[][] board;

    public PlayBoard() {
        this.alivePieces = new ArrayList<>();
        this.board = new Loader[ROW_NUM][COL_NUM];
    }

    public Loader get(int row, int col) {
        return board[row][col];
    }

    private Loader getPieceLoader(Piece piece) {
        return this.get(piece.getRow(), (piece.getCol()));
    }

    private boolean canCapture(Piece capturer, Piece capturee) {
        if (capturer.getPlayer() == capturee.getPlayer()) return false;
        if (getPieceLoader(capturee).getType() == TRAP) return true;
        switch (capturer.getType()) {
            case ELEPHANT -> {
                return capturee.getType() != RAT;
            }
            case RAT -> {
                if (getPieceLoader(capturer).getType() == WATER)
                    return getPieceLoader(capturee).getType() == WATER;
                else return capturee.getType() == RAT || capturee.getType() == ELEPHANT;
            }
            default -> {
                return capturee.getRank() <= capturer.getRank();
            }
        }
    }
    // check for illegality of movement assume the destination landscape is empty

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

    public int[] findDestination(Movable movable) throws LogicException {
        int row = movable.getRow();
        int col = movable.getCol();
        switch (movable.getDirection()) {
            case UP -> --row;
            case DOWN -> ++row;
            case LEFT -> --col;
            case RIGHT -> ++col;
        }
        if (!(row >= 0 && row < ROW_NUM && col >= 0 && col < COL_NUM))
            throw new OutBoardException(movable);
        if((movable.getType() == LION || movable.getType() == TIGER) && get(row, col).getType() == WATER) {
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
        if (!get(row, col).canLoad(movable))
            throw new NotLoadableException(get(row, col), movable);
        else if ((get(row, col).getType() == DEN && ((Den) get(row, col)).getPlayer() == ((Piece) movable).getPlayer()))
            throw new LogicException("Pieces cannot step on DEN of the same side!");
        return new int[]{row, col};
    }

    public void doMove(Movable movable) throws LogicException {
        Piece piece = (Piece) movable;
        int[] dest = findDestination(movable);
        Piece target = (Piece) get(dest[0], dest[1]).getLoad();
        if (target != null && !canCapture(piece, target))
            throw new IllegalCaptureException(movable, target);
        if (target != null) {
            target.die();
            alivePieces.remove(target);
            get(dest[0], dest[1]).setLoad(null);
        }
        getPieceLoader(piece).setLoad(null);
        piece.move(dest[0], dest[1]);
        get(dest[0], dest[1]).setLoad(movable);
    }

    void put(Loader loader) {
        Landscape landscape;
        if (loader instanceof Landscape) landscape = (Landscape) loader;
        else throw new IllegalArgumentException();
        board[landscape.getRow()][landscape.getCol()] = loader;
    }


    @SneakyThrows
    void initBoard(Player playerX, Player playerY) {
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
