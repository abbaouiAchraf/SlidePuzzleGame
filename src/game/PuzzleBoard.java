package game;

import java.awt.*;

public class PuzzleBoard extends AbstractPuzzleBoard {
    public PuzzleBoard(IPuzzleBoardImplementation boardImpl) {
        super(boardImpl);
    }
    @Override
    public boolean move(Direction direction) {
        Point emptyTilePos = boardImpl.getEmptyTilePosition();
        int x = emptyTilePos.x;
        int y = emptyTilePos.y;

        switch (direction) {
            case UP:
                if (y < boardImpl.getSize() - 1) {
                    boardImpl.swapTiles(x, y, x, y + 1);
                    notifyObservers();
                    return true;
                }
                break;
            case DOWN:
                if (y > 0) {
                    boardImpl.swapTiles(x, y, x, y - 1);
                    notifyObservers();
                    return true;
                }
                break;
            case LEFT:
                if (x < boardImpl.getSize() - 1) {
                    boardImpl.swapTiles(x, y, x + 1, y);
                    notifyObservers();
                    return true;
                }
                break;
            case RIGHT:
                if (x > 0) {
                    boardImpl.swapTiles(x, y, x - 1, y);
                    notifyObservers();
                    return true;
                }
                break;
        }

        return false;
    }

    @Override
    public boolean isSolved() {
        int value = 1;
        for (int y = 0; y < boardImpl.getSize(); y++) {
            for (int x = 0; x < boardImpl.getSize(); x++) {
                if (y == boardImpl.getSize() - 1 && x == boardImpl.getSize() - 1) {
                    return boardImpl.getTile(x, y).getValue() == 0;
                }

                if (boardImpl.getTile(x, y).getValue() != value) {
                    return false;
                }

                value++;
            }
        }

        return true;
    }

    @Override
    public void shuffle(IShuffleStrategy strategy) {
        strategy.shuffle(boardImpl);
        notifyObservers();
    }
}
