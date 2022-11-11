package group11.comp3211.model;

import group11.comp3211.model.landscape.Landscape;
import group11.comp3211.model.piece.Piece;

import java.io.Serializable;

public final class PlayBoard implements Serializable {
    public static final int ROW_NUM = 9;
    public static final int COL_NUM = 7;
    private final Loader[][] board;

    public PlayBoard() {
        this.board = new Loader[ROW_NUM][COL_NUM];
    }

    public boolean canMove(Movable movable, int dRow, int dCol) {
        return false;
    }


    public void doMove(Movable movable) {
        Piece piece = (Piece) movable;
        System.out.println(piece + " moves " + piece.getDirection());
    }

    public void doMove(Movable movable, int dRow, int dCol) {

    }

    public void put(Piece piece) {

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
}
