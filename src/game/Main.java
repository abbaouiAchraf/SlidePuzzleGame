package game;

import musicRunner.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    /*
    1. Edit the SlidePuzzleGame.java to adjust game difficulty level.
    2. Edit Random int value to use different templates.
     */
    private static BackgroundMusicPlayer musicPlayer;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Slide Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());
        frame.setBackground(new Color(240, 240, 240));

        musicPlayer = new BackgroundMusicPlayer("./resources/assets/clips/music.wav");
        musicPlayer.play();

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(90, 90, 90));
        titlePanel.setPreferredSize(new Dimension(400, 50));
        JLabel titleLabel = new JLabel("Choose difficulty level:");
        titleLabel.setForeground(new Color(240, 240, 240));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton easyButton = new JButton("Easy (3x3)");
        easyButton.setBackground(new Color(90, 90, 90));
        easyButton.setForeground(new Color(240, 240, 240));
        easyButton.setFont(new Font("Arial", Font.PLAIN, 18));
        easyButton.addActionListener(e -> {
            startGame(3);
            musicPlayer.stop();
            frame.dispose();
            try {
                playSoundAction("./resources/assets/clips/click.wav");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(easyButton);

        JButton mediumButton = new JButton("Medium (4x4)");
        mediumButton.setBackground(new Color(90, 90, 90));
        mediumButton.setForeground(new Color(240, 240, 240));
        mediumButton.setFont(new Font("Arial", Font.PLAIN, 18));
        mediumButton.addActionListener(e -> {
            startGame(4);
            musicPlayer.stop();
            frame.dispose();
            try {
                playSoundAction("./resources/assets/clips/click.wav");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(mediumButton);

        JButton hardButton = new JButton("Hard (5x5)");
        hardButton.setBackground(new Color(90, 90, 90));
        hardButton.setForeground(new Color(240, 240, 240));
        hardButton.setFont(new Font("Arial", Font.PLAIN, 18));
        hardButton.addActionListener(e -> {
            startGame(5);
            musicPlayer.stop();
            frame.dispose();
            try {
                playSoundAction("./resources/assets/clips/click.wav");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(hardButton);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void startGame(int size) {
        SlidePuzzleGame game = new SlidePuzzleGame(size);
        game.init();
    }
    private static void playSoundAction(String filepath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File MusicFile = new File(filepath);
        Clip Clip;
        AudioInputStream winAudioInputStream = AudioSystem.getAudioInputStream(MusicFile);
        Clip = AudioSystem.getClip();
        Clip.open(winAudioInputStream);
        Clip.start();
    }
}
