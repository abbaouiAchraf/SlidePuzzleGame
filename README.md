# SlidePuzzleGame
This project is a sliding puzzle game implementation in Java using various design patterns. The goal of the game is to rearrange tiles on a board to form a specific pattern or arrangement. In this implementation, the player must rearrange a 3x3|4x4|5x5 grid of numbered tiles by sliding them into the empty space until they are in numerical order.

## Design Patterns
This project makes use of the following design patterns:

- **Bridge Pattern**: to separate the abstraction and implementation of the puzzle board
- **Observer Pattern**: to notify the game UI of changes to the puzzle board
- **Strategy Pattern**: to shuffle the puzzle board using different strategies
## Classes
Here is a brief overview of the main classes in this project:

- **AbstractPuzzleBoard**: the abstraction of the puzzle board, containing methods for moving tiles, shuffling the board, and notifying observers of changes
- **IPuzzleBoardImplementation**: the interface defining the implementation of the puzzle board, including methods for getting and setting tiles and getting the position of the empty tile
- **NByNPuzzleBoard**: the implementation of the puzzle board for an N-by-N sliding puzzle
- **SlidePuzzleGameUI**: the game UI, which implements the IBoardObserver interface to receive updates from the puzzle board
- **IShuffleStrategy**: the interface defining different strategies for shuffling the puzzle board
- **RandomShuffleStrategy**: a strategy for shuffling the puzzle board randomly
- **SystematicShuffleStrategy**: a strategy for shuffling the puzzle board in a systematic manner using a fixed number of moves
- **SlidePuzzleGame**: the main game controller, which initializes the game, runs the game loop, and handles user input
- **PuzzleMoveThread**: a separate thread for moving a tile on the puzzle board
## Getting Started
To run the game, you will need Java installed on your machine. Simply clone the repository and run the SlidePuzzleGame class. The game should open in a new window and you can start playing by clicking on tiles to move them into the empty space.

## Credits
This project was created by ABBAOUI Achraf as a School project. If you have any questions or comments, please reach out to me at achraf.abbaoui2001@gmail.com.
