package game;

import java.awt.Point;

public interface IPuzzleBoardImplementation {
    Tile getTile(int x, int y);
    void setTile(int x, int y, Tile tile);
    Point getEmptyTilePosition();

    int getSize();

    void swapTiles(int x, int y, int x1, int i);
}
