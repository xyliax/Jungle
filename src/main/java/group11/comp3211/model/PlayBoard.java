package group11.comp3211.model;

import group11.comp3211.common.exceptions.IllegalMovementException;
import group11.comp3211.model.landscape.*;
import group11.comp3211.model.piece.*;
import lombok.Builder;

import java.io.Serializable;

public final class PlayBoard implements Serializable {
    public static final int ROW_NUM = 9;
    public static final int COL_NUM = 7;
    private final Loader[][] board;

    public PlayBoard() {
        this.board = new Loader[ROW_NUM][COL_NUM];
    }

    public Loader getLoader(Movable movable) {
        Piece piece = (Piece) movable;
        return this.get(piece.getRow(), piece.getCol());
    }

    public boolean canCapture(Movable movable1, Movable movable2) {
        Piece piece1 = (Piece) movable1;
        Piece piece2 = (Piece) movable2;
        if (piece2.getPlayer() == piece1.getPlayer()) return false;
        if (getLoader(piece2).type() == JungleType.TRAP) return true;
        switch(piece1.getType()) {
            case ELEPHANT -> {
                return piece2.getType() != JungleType.RAT;
            }
            case RAT -> {
                if(getLoader(piece1).type() == JungleType.RIVER)
                    return getLoader(piece2).type() == JungleType.RIVER;
                else
                    return piece2.getType() == JungleType.RAT || piece2.getType() == JungleType.ELEPHANT;
            }
            default -> {
                return piece2.getRank() <= piece1.getRank();
            }
        }
    }

    // check for illegality of movement assume the destination landscape is empty
    public boolean ratInRiver(JungleType jungleType) {
        switch (jungleType) {
            case RIVERAREALEFT -> {
                for(int i=3; i<=5; ++i)
                    for(int j=1; j<=2; ++j)
                        if (getLoad(i, j) != null) return true;
            }
            case RIVERAREARIGHT -> {
                for(int i=3; i<=5; ++i)
                    for(int j=4; j<=5; ++j)
                        if (getLoad(i, j) != null) return true;
            }
        }
        return false;
    }

    public int[] canMove(Movable movable) throws IllegalMovementException {
        Piece piece = (Piece) movable;
        int row = piece.getRow();
        int col = piece.getCol();
        switch(piece.getType()) {
            case LION, TIGER -> {
                switch (piece.getDirection()) {
                    case UP -> {
                        if (row == 0)   throw new IllegalMovementException("Trying to get out of the board. ");
                        if (getType(row-1, col) == JungleType.RIVER) {
                            if (ratInRiver(((River) get(row-1, col)).getBelong2())) throw new IllegalMovementException("Cannot jump over the river because there is a rat in. ");
                            else row -= 4;
                        }
                        else --row;
                    }
                    case DOWN -> {
                        if (row == 8)   throw new IllegalMovementException("Trying to get out of the board. ");
                        if (getType(row+1, col) == JungleType.RIVER) {
                            if (ratInRiver(((River) get(row+1, col)).getBelong2())) throw new IllegalMovementException("Cannot jump over the river because there is a rat in. ");
                            else row += 4;
                        }
                        else ++row;
                    }
                    case LEFT -> {
                        if (col == 0)   throw new IllegalMovementException("Trying to get out of the board. ");
                        if (getType(row, col-1) == JungleType.RIVER) {
                            if (ratInRiver(((River) get(row, col-1)).getBelong2())) throw new IllegalMovementException("Cannot jump over the river because there is a rat in. ");
                            else col -= 3;
                        }
                        else --col;
                    }
                    case RIGHT -> {
                        if (col == 6)   throw new IllegalMovementException("Trying to get out of the board. ");
                        if (getType(row, col+1) == JungleType.RIVER) {
                            if (ratInRiver(((River) get(row, col+1)).getBelong2())) throw new IllegalMovementException("Cannot jump over the river because there is a rat in. ");
                            else col += 3;
                        }
                        else ++col;
                    }
                }
            }
            case RAT -> {
                switch (piece.getDirection()) {
                    case UP -> --row;
                    case DOWN -> ++row;
                    case LEFT -> --col;
                    case RIGHT -> ++col;
                }
                if(row < 0 || row > 8 || col < 0 || col > 6) throw new IllegalMovementException("Trying to get out of the board. ");
            }
            default -> {
                switch (piece.getDirection()) {
                    case UP -> --row;
                    case DOWN -> ++row;
                    case LEFT -> --col;
                    case RIGHT -> ++col;
                }
                if(row < 0 || row > 8 || col < 0 || col > 6) throw new IllegalMovementException("Trying to get out of the board. ");
                if(get(row, col).type() == JungleType.RIVER) throw new IllegalMovementException("Cannot get into the river. ");
            }
        }
        return new int[] {row, col};
    }

    // Move HERE!!!
    public void doMove(Movable movable) throws IllegalMovementException {
        Piece piece = (Piece) movable;
        // System.out.println(piece + " moves " + piece.getDirection());
        int[] dest = canMove(movable);
        Piece target = (Piece) getLoad(dest[0], dest[1]);
        if(target != null && !canCapture(movable, target))
            throw new IllegalMovementException("Cannot capture the piece. ");
        if(target != null) {
            target.die();
            get(dest[0], dest[1]).setLoad(null);
        }
        getLoader(movable).setLoad(null);
        piece.move(dest[0], dest[1]);
        get(dest[0], dest[1]).setLoad(movable);
    }

    public void put(Loader loader) {
        Landscape landscape;
        if (loader instanceof Landscape)
            landscape = (Landscape) loader;
        else throw new IllegalArgumentException();
        board[landscape.getRow()][landscape.getCol()] = loader;
    }

    public Loader get(int row, int col) {
        return board[row][col];
    }

    public Movable getLoad(int row, int col) {
        return board[row][col].getLoad();
    }

    public JungleType getType(int row, int col) {
        return board[row][col].type();
    }
}
