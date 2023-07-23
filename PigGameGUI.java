// codeswithpankaj.com

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class PigGameGUI extends JFrame {
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JButton rollButton;
    private JButton holdButton;
    private JButton newGameButton;
    private JButton saveGameButton;
    private JTextArea gameLogTextArea;
    private JScrollPane gameLogScrollPane;

    private int player1TotalScore;
    private int player2TotalScore;
    private boolean isPlayer1Turn;
    private Random random;
    private StringBuilder gameLog;
    private ArrayList<String> historyRecords;

    public PigGameGUI() {
        setTitle("Game of Pig");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        player1ScoreLabel = new JLabel("Player 1 Score: 0");
        player2ScoreLabel = new JLabel("Player 2 Score: 0");
        rollButton = new JButton("Roll");
        holdButton = new JButton("Hold");
        newGameButton = new JButton("New Game");
        saveGameButton = new JButton("Save Game");
        gameLogTextArea = new JTextArea(10, 30);
        gameLogScrollPane = new JScrollPane(gameLogTextArea);

        JPanel scorePanel = new JPanel();
        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rollButton);
        buttonPanel.add(holdButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(saveGameButton);

        add(scorePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(gameLogScrollPane, BorderLayout.SOUTH);

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int roll = rollDice();
                gameLog.append(getCurrentPlayerName()).append(" rolled a ").append(roll).append("\n");
                updateGameLog();
                if (roll == 1) {
                    gameLog.append(getCurrentPlayerName()).append(" scored 0 this round.\n");
                    updateGameLog();
                    switchPlayerTurn();
                } else {
                    addToCurrentPlayerScore(roll);
                }
            }
        });

        holdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLog.append(getCurrentPlayerName()).append(" chose to hold.\n");
                updateGameLog();
                switchPlayerTurn();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGameHistory();
            }
        });

        random = new Random();
        gameLog = new StringBuilder();
        historyRecords = new ArrayList<>();
        startNewGame();
    }

    private int rollDice() {
        return random.nextInt(6) + 1;
    }

    private String getCurrentPlayerName() {
        return isPlayer1Turn ? "Player 1" : "Player 2";
    }

    private void addToCurrentPlayerScore(int score) {
        if (isPlayer1Turn) {
            player1TotalScore += score;
            player1ScoreLabel.setText("Player 1 Score: " + player1TotalScore);
            if (player1TotalScore >= 100) {
                gameLog.append("Player 1 wins with ").append(player1TotalScore).append(" points!\n");
                updateGameLog();
                disableButtons();
                saveGameHistory();
            }
        } else {
            player2TotalScore += score;
            player2ScoreLabel.setText("Player 2 Score: " + player2TotalScore);
            if (player2TotalScore >= 100) {
                gameLog.append("Player 2 wins with ").append(player2TotalScore).append(" points!\n");
                updateGameLog();
                disableButtons();
                saveGameHistory();
            }
        }
    }

    private void switchPlayerTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        gameLog.append("It's ").append(getCurrentPlayerName()).append("'s turn.\n");
        updateGameLog();
    }

    private void startNewGame() {
        player1TotalScore = 0;
        player2TotalScore = 0;
        isPlayer1Turn = true;

        player1ScoreLabel.setText("Player 1 Score: 0");
        player2ScoreLabel.setText("Player 2 Score: 0");
        gameLog.delete(0, gameLog.length());
        gameLog.append("New game started.\n");
        updateGameLog();
        enableButtons();
    }

    private void updateGameLog() {
        gameLogTextArea.setText(gameLog.toString());
        gameLogTextArea.setCaretPosition(gameLogTextArea.getDocument().getLength());
    }

    private void disableButtons() {
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
    }

    private void enableButtons() {
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }

    private void saveGameHistory() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = dateFormat.format(new Date());
        String winner = (player1TotalScore >= 100) ? "Player 1" : "Player 2";
        String totalPoints = (player1TotalScore >= 100) ? String.valueOf(player1TotalScore) : String.valueOf(player2TotalScore);
        historyRecords.add("Game result: " + winner + " wins");
        historyRecords.add("Date and time: " + dateTime);
        historyRecords.add("Total points: " + totalPoints);
        historyRecords.add("Player: " + "2 Players");

        try (FileWriter writer = new FileWriter("game_history.txt", true)) {
            for (String record : historyRecords) {
                writer.write(record + System.lineSeparator());
            }
            writer.write("---------------------------" + System.lineSeparator());
            writer.flush();
            JOptionPane.showMessageDialog(this, "Game history saved successfully.", "Save Game History", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving game history.", "Save Game History", JOptionPane.ERROR_MESSAGE);
        }

        historyRecords.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PigGameGUI().setVisible(true);
            }
        });
    }
}
