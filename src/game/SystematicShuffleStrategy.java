package game;

import java.awt.*;
import java.util.Random;

public class SystematicShuffleStrategy implements IShuffleStrategy{
    private int shuffleMoves;

    public SystematicShuffleStrategy(int shuffleMoves) {
        this.shuffleMoves = shuffleMoves;
    }

    @Override
    public void shuffle(IPuzzleBoardImplementation board) {
        Direction[] directions = Direction.values();
        Random random = new Random();
        Point emptyTilePos;
        int x, y;

        for (int i = 0; i < shuffleMoves; i++) {
            emptyTilePos = board.getEmptyTilePosition();
            x = emptyTilePos.x;
            y = emptyTilePos.y;

            while (true) {
                Direction randomDirection = directions[random.nextInt(directions.length)];
                int newX = x;
                int newY = y;

                switch (randomDirection) {
                    case UP:
                        newY = y + 1;
                        break;
                    case DOWN:
                        newY = y - 1;
                        break;
                    case LEFT:
                        newX = x + 1;
                        break;
                    case RIGHT:
                        newX = x - 1;
                        break;
                }

                if (newX >= 0 && newX < board.getSize() && newY >= 0 && newY < board.getSize()) {
                    board.swapTiles(x, y, newX, newY);
                    break;
                }
            }
        }
    }
}
