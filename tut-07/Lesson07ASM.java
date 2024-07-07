import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Lesson07ASM {
  private enum Player {
    RED,
    YELLOW
  }

  private static final int NUM_OF_COLUMNS = 7;
  private static final int NUM_OF_ROWS = 6;

  private static boolean IsRedTurn;
  private static ArrayList<ArrayList<Player>> board; // For recording the state of the board

  private static JLabel[][] holes;
  private static JButton[] buttons;

  public static void main(String[] args) {
    JFrame frame = new JFrame("Connect Four");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 600);

    JPanel panel = new JPanel();
    GridLayout layout = new GridLayout(NUM_OF_ROWS + 1, NUM_OF_COLUMNS); // One more row for buttons, so + 1
    layout.setVgap(10);
    layout.setHgap(10);
    panel.setLayout(layout);

    // Init
    board = new ArrayList<>();
    initIsRedTurn();
    holes = new JLabel[NUM_OF_ROWS][NUM_OF_COLUMNS];
    buttons = new JButton[NUM_OF_COLUMNS];

    // Add buttons bar to the panel
    for (int i = 0; i < NUM_OF_COLUMNS; i++) {
      board.add(new ArrayList<>());

      buttons[i] = new JButton("V");
      JButton button = buttons[i];
      button.setActionCommand(String.valueOf(i));
      button.setBackground(IsRedTurn ? Color.RED : Color.YELLOW);

      // Add Click event listener
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JButton button = (JButton) e.getSource();
          int column = Integer.parseInt(button.getActionCommand());
          int row = board.get(column).size();

          // If the column is full, don't do anything
          if (row < 0) {
            JOptionPane.showMessageDialog(null, "Column is full! Please choose another column.");
            return;
          }

          // Set the color of the hole
          holes[NUM_OF_ROWS - row - 1][column].setBackground(IsRedTurn ? Color.RED : Color.YELLOW);

          // Insert record to the board
          Player currentPlayer = IsRedTurn ? Player.RED : Player.YELLOW;
          board.get(column).add(currentPlayer);

          // Check if the player has won
          boolean isWon = checkWin(column, row, currentPlayer);
          if (isWon) {
            JOptionPane.showMessageDialog(null, "Player " + currentPlayer.toString() + " wins!");
            resetGame(holes);
            return;
          }

          // Switch player
          switchPlayer();
        }
      });

      // Add button to the panel
      panel.add(button);
    }

    for (int i = 0; i < NUM_OF_ROWS; i++) {
      for (int j = 0; j < NUM_OF_COLUMNS; j++) {
        holes[i][j] = new JLabel();
        holes[i][j].setOpaque(true);
        holes[i][j].setBackground(Color.WHITE);

        // Add hole to the panel
        panel.add(holes[i][j]);
      }
    }

    frame.add(panel);
    frame.setVisible(true);
  }

  private static void switchPlayer() {
    IsRedTurn = !IsRedTurn;

    // Change the color of the buttons
    for (JButton button : buttons) {
      button.setBackground(IsRedTurn ? Color.RED : Color.YELLOW);
    }
  }

  private static void initIsRedTurn() {
    IsRedTurn = true;
  }

  private static void resetGame(JLabel[][] holes) {
    initIsRedTurn();

    for (int j = 0; j < NUM_OF_COLUMNS; j++) {
      // Clear the board
      board.get(j).clear();

      // Clear the holes
      for (int i = 0; i < NUM_OF_ROWS; i++) {
        holes[i][j].setOpaque(true);
        holes[i][j].setBackground(Color.WHITE);
      }
    }
  }

  private static Player getTokenColor(int x, int y) {
    ArrayList<Player> column = board.get(x);

    if (column.size() > y) {
      return column.get(y);
    } else {
      return null;
    }
  }

  private static boolean checkLine(int x1, int y1, int xDiff, int yDiff, Player player) {
    for (int i = 0; i < 4; ++i) {
      int x = x1 + (xDiff * i);
      int y = y1 + (yDiff * i);

      if (x < 0 || x > NUM_OF_COLUMNS - 1) {
        return false;
      }

      if (y < 0 || y > NUM_OF_ROWS - 1) {
        return false;
      }

      if (player != getTokenColor(x, y)) {
        return false;
      }
    }

    return true;
  }

  private static boolean checkWin(int x, int y, Player player) {
    // Vertical line
    if (checkLine(x, y, 0, -1, player)) {
      return true;
    }

    for (int offset = 0; offset < 4; offset++) {
      // Horizontal line
      if (checkLine(x - 3 + offset, y, 1, 0, player)) {
        return true;
      }

      // Leading diagonal
      if (checkLine(x - 3 + offset, y + 3 - offset, 1, -1, player)) {
        return true;
      }

      // Trailing diagonal
      if (checkLine(x - 3 + offset, y - 3 + offset, 1, 1, player)) {
        return true;
      }
    }

    return false;
  }
}