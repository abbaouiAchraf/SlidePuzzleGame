package game;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SlidePuzzleGameUI extends JFrame implements IBoardObserver {
    private AbstractPuzzleBoard puzzleBoard;
    private JPanel boardPanel;
    private Image[] tileImages;
    private int tileSize = 100;
    private int clicks = 0;
    private String imagePath;

    public SlidePuzzleGameUI(AbstractPuzzleBoard puzzleBoard) {
        this.puzzleBoard = puzzleBoard;
        initUI();
        loadTileImages();
    }
    private void initUI() {
        // Set up main frame
        setTitle("Slide Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(new Color(238, 238, 238));

        // Create label for move counter
        JLabel label = new JLabel("Moves: " + clicks);
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        label.setForeground(new Color(78, 78, 78));
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Create button for help
        JButton helpButton = new JButton("Help");
        helpButton.setFocusPainted(false);
        helpButton.setBackground(new Color(60, 184, 120));
        helpButton.setForeground(Color.WHITE);
        helpButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        helpButton.addActionListener(e -> {
            try {
                Help();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create button for reset
        JButton resetButton = new JButton("Change");
        resetButton.setFocusPainted(false);
        resetButton.setBackground(new Color(255, 0, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resetButton.addActionListener(e -> {
            try {
                playSoundAction("./resources/assets/clips/click.wav");
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
            SlidePuzzleGame game = new SlidePuzzleGame(puzzleBoard.boardImpl.getSize());
            game.init();
            this.dispose();
            clicks = 0;
            label.setText("Moves: " + clicks);
            boardPanel.repaint();
        });

        // Create button for back
        JButton backButton = new JButton("Back");
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(0, 0, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(e -> {
            // TODO: Handle back button click
            Main.main(new String[]{});
            this.dispose();
        });


        // Create panel for controls
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(238, 238, 238));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(Box.createHorizontalGlue());
        controlPanel.add(label);
        controlPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        controlPanel.add(helpButton);
        controlPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        controlPanel.add(resetButton);
        controlPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        controlPanel.add(backButton);
        controlPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        // controlPanel.add(homeButton);

        // Create board panel
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setBackground(new Color(255, 255, 255));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        boardPanel.setPreferredSize(new Dimension(tileSize * puzzleBoard.boardImpl.getSize(),
                tileSize * puzzleBoard.boardImpl.getSize()));
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    handleInput(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                label.setText("Moves: " + clicks);
            }
        });

        // Add components to main frame
        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void Help() throws Exception {
        List<Direction> moveSequence = Arrays.asList(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT);
        long delayBetweenMoves = 500; // 1 second

        PuzzleMoveThread moveThread = new PuzzleMoveThread(puzzleBoard, moveSequence, delayBetweenMoves);
        moveThread.start();
    }

    @Override
    public void update(AbstractPuzzleBoard board) {
        this.puzzleBoard = board;
        boardPanel.repaint();
    }
    private void drawBoard(Graphics g) {
        for (int y = 0; y < puzzleBoard.boardImpl.getSize(); y++) {
            for (int x = 0; x < puzzleBoard.boardImpl.getSize(); x++) {
                Tile tile = puzzleBoard.boardImpl.getTile(x, y);
                int tileValue = tile.getValue();
                if (tileValue >= 0 && tileValue < tileImages.length) {
                    g.drawImage(tileImages[tileValue], x * tileSize, y * tileSize, tileSize, tileSize, null);
                    repaint();
                }
            }
        }
    }


    private void handleInput(MouseEvent e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int x = e.getX() / tileSize;
        int y = e.getY() / tileSize;

        System.out.println("Clicked tile position: (" + x + ", " + y + ")");

        Direction moveDirection = null;
        Point emptyTilePos = puzzleBoard.boardImpl.getEmptyTilePosition();

        System.out.println("Empty tile position: " + emptyTilePos);

        if (emptyTilePos.x == x) {
            if (emptyTilePos.y - y == 1) {
                moveDirection = Direction.DOWN;
            } else if (emptyTilePos.y - y == -1) {
                moveDirection = Direction.UP;
            }
        } else if (emptyTilePos.y == y) {
            if (emptyTilePos.x - x == 1) {
                moveDirection = Direction.RIGHT;
            } else if (emptyTilePos.x - x == -1) {
                moveDirection = Direction.LEFT;
            }
        }

        if (moveDirection != null) {
            clicks++;
            System.out.println("Moving in direction: " + moveDirection);
            puzzleBoard.move(moveDirection);
            playSoundAction("./resources/assets/clips/touch.wav");
            boardPanel.repaint();
            if (puzzleBoard.isSolved()) {
                playSoundAction("./resources/assets/clips/winner.wav");
                JOptionPane.showMessageDialog(this, "You solved the puzzle!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                Desktop.getDesktop().open(new File("resources" + imagePath));
                Main.main(new String[]{});
                this.dispose();
            }
        } else {
            System.out.println("No valid move direction");
        }
    }
    private void loadTileImages() {
        int numberOfTiles = puzzleBoard.boardImpl.getSize() * puzzleBoard.boardImpl.getSize() - 1;
        tileImages = new Image[numberOfTiles + 1];
        int randomInt = getRandomNumberInRange(1, 3);
        System.out.println(numberOfTiles);
        if(numberOfTiles == 8){
            try {
                for (int i = 1; i <= numberOfTiles; i++) {
                    tileImages[i] = ImageIO.read(new File("./resources/assets/_3x3/" + randomInt + "/" + i + ".png"));
                }
                tileImages[0] = ImageIO.read(new File("./resources/assets/_3x3/" + randomInt+ "/empty.png"));
                imagePath = "/assets/_3x3/" + randomInt+ "/full.png";
            } catch (IOException e) {
                System.err.println("Error loading tile images.");
                e.printStackTrace();
            }
        } else if (numberOfTiles == 15) {
            try {
                for (int i = 1; i <= numberOfTiles; i++) {
                    tileImages[i] = ImageIO.read(new File("./resources/assets/_4x4/" + randomInt + "/" + i + ".png"));
                }
                tileImages[0] = ImageIO.read(new File("./resources/assets/_4x4/" + randomInt+ "/empty.png"));
                imagePath = "/assets/_4x4/" + randomInt+ "/full.png";
            } catch (IOException e) {
                System.err.println("Error loading tile images.");
                e.printStackTrace();
            }
        } else if (numberOfTiles == 24) {
            try {
                for (int i = 1; i <= numberOfTiles; i++) {
                    tileImages[i] = ImageIO.read(new File("./resources/assets/_5x5/" + randomInt + "/" + i + ".png"));
                }
                tileImages[0] = ImageIO.read(new File("./resources/assets/_4x4/" + randomInt+ "/empty.png"));
                imagePath = "/assets/_5x5/" + randomInt+ "/full.png";
            } catch (IOException e) {
                System.err.println("Error loading tile images.");
                e.printStackTrace();
            }
        }

    }
    private static int getRandomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
    private void playSoundAction(String filepath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File MusicFile = new File(filepath);
        Clip Clip;
        AudioInputStream winAudioInputStream = AudioSystem.getAudioInputStream(MusicFile);
        Clip = AudioSystem.getClip();
        Clip.open(winAudioInputStream);
        Clip.start();
    }
}
