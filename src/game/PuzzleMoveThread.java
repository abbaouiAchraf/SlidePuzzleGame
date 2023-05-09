package game;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PuzzleMoveThread extends Thread {
    private AbstractPuzzleBoard puzzleBoard;
    private List<Direction> moveSequence;
    private long delayBetweenMoves;

    public PuzzleMoveThread(AbstractPuzzleBoard puzzleBoard, List<Direction> moveSequence, long delayBetweenMoves) {
        this.puzzleBoard = puzzleBoard;
        this.moveSequence = moveSequence;
        this.delayBetweenMoves = delayBetweenMoves;
    }

    @Override
    public void run() {
        for (Direction direction : moveSequence) {
            if (puzzleBoard.move(direction)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(delayBetweenMoves);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
