package game;

import java.awt.*;

public class NByNPuzzleBoard implements IPuzzleBoardImplementation{
    private Tile[][] board;
    private Point emptyTilePos;
    private int size;

    public NByNPuzzleBoard(int size) {
        this.size = size;
        this.board = new Tile[size][size];
        initBoard();
    }
    private void initBoard() {
        int value = 1;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y == size - 1 && x == size - 1) {
                    board[x][y] = new Tile(0);
                } else {
                    board[x][y] = new Tile(value++);
                }
            }
        }
        emptyTilePos = new Point(size - 1, size - 1);
    }

    @Override
    public Tile getTile(int x, int y) {
        return board[x][y];
    }

    @Override
    public void setTile(int x, int y, Tile tile) {
        board[x][y] = tile;
    }

    @Override
    public Point getEmptyTilePosition() {
        return emptyTilePos;
    }

    public int getSize() {
        return size;
    }

    public void swapTiles(int x1, int y1, int x2, int y2) {
        Tile temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
        if (board[x1][y1].getValue() == 0) {
            emptyTilePos.setLocation(x1, y1);
        } else if (board[x2][y2].getValue() == 0) {
            emptyTilePos.setLocation(x2, y2);
        }
    }
}
