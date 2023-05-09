package game;

import java.awt.*;
import java.util.Random;

public class RandomShuffleStrategy implements IShuffleStrategy{
    private int shuffleMoves;

    public RandomShuffleStrategy(int shuffleMoves) {
        this.shuffleMoves = shuffleMoves;
    }

    @Override
    public void shuffle(IPuzzleBoardImplementation board) {
        Random random = new Random();
        Direction[] directions = Direction.values();

        for (int i = 0; i < shuffleMoves; i++) {
            Direction direction = directions[random.nextInt(directions.length)];
            Point emptyTilePos = board.getEmptyTilePosition();
            int x = emptyTilePos.x;
            int y = emptyTilePos.y;
            boolean moved = false;

            switch (direction) {
                case UP:
                    if (y < board.getSize() - 1) {
                        board.swapTiles(x, y, x, y + 1);
                        moved = true;
                    }
                    break;
                case DOWN:
                    if (y > 0) {
                        board.swapTiles(x, y, x, y - 1);
                        moved = true;
                    }
                    break;
                case LEFT:
                    if (x < board.getSize() - 1) {
                        board.swapTiles(x, y, x + 1, y);
                        moved = true;
                    }
                    break;
                case RIGHT:
                    if (x > 0) {
                        board.swapTiles(x, y, x - 1, y);
                        moved = true;
                    }
                    break;
            }

            if (moved) {
                i++;
            }
        }
    }
}
