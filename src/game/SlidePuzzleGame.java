package game;

import javax.swing.*;

public class SlidePuzzleGame {
    private AbstractPuzzleBoard puzzleBoard;
    private SlidePuzzleGameUI gameUI;
    private IShuffleStrategy shuffleStrategy;

    public SlidePuzzleGame(int boardSize) {
        IPuzzleBoardImplementation boardImpl = new NByNPuzzleBoard(boardSize);
        this.puzzleBoard = new PuzzleBoard(boardImpl);
        this.gameUI = new SlidePuzzleGameUI(puzzleBoard);
        this.shuffleStrategy = new SystematicShuffleStrategy(500); //Hard Real Mode of Playing
        // this.shuffleStrategy = new RandomShuffleStrategy(50); //Easy Testing Mode of Playing

        // I can add filter mode for difficulty and quality scaling mode.
        /*
        Both classes shuffle a puzzle board by making a specified number of random moves.
        The SystematicShuffleStrategy class iterates through all directions for each move,
        while the RandomShuffleStrategy class selects one direction at random for each move.
        The SystematicShuffleStrategy may take longer to complete a shuffle than the RandomShuffleStrategy,
        but it is more systematic in its approach.
         */
    }

    public void init() {
        SwingUtilities.invokeLater(() -> {
            gameUI.setVisible(true);
            puzzleBoard.shuffle(shuffleStrategy);
        });
    }

    public AbstractPuzzleBoard getPuzzleBoard() {
        return puzzleBoard;
    }
}
