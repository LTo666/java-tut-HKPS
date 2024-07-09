import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

class Progress implements Runnable {
  private final JProgressBar bar;
  private final int ms;
  private boolean isReverted = false;
  private final JButton starButton;

  public Progress(JProgressBar bar, JButton starButton, int ms) {
    this.bar = bar;
    this.starButton = starButton;
    this.ms = ms;
  }

  public void setRevert(boolean isReverted) {
    if (this.isReverted == isReverted) {
      return;
    }

    this.isReverted = isReverted;

    final Color previousBackgroundColor = bar.getBackground();
    final Color previousForegroundColor = bar.getForeground();

    this.bar.setBackground(isReverted ? previousForegroundColor : previousBackgroundColor);
    this.bar.setForeground(isReverted ? previousBackgroundColor : previousForegroundColor);
    this.bar.setValue(isReverted ? 100 : 0);
  }

  public void run() {
    this.starButton.setEnabled(false);

    if (this.isReverted) {
      for (int i = 100; i >= 0; i--) {
        bar.setValue(i);

        try {
          Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
      }
    } else {
      for (int i = 0; i <= 100; i++) {
        bar.setValue(i);

        try {
          Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
      }
    }

    this.starButton.setEnabled(true);
  }
}

public class Lesson09ASM {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Progress");
    frame.setLayout(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);

    final int NUM_OF_BAR = 2;
    final int DELAY_INTERVAL = 100;

    for (int i = 0; i < NUM_OF_BAR; i++) {
      final int IDX = i + 1;
      final int DELAY = DELAY_INTERVAL * IDX;
      final int BUTTON_HEIGHT = 60;
      final int BAR_HEIGHT = BUTTON_HEIGHT / 3;

      JButton button = new JButton("Start " + DELAY + "ms");
      button.setSize(150, BUTTON_HEIGHT);
      button.setActionCommand(String.valueOf(i));
      button.setLocation(BUTTON_HEIGHT, BUTTON_HEIGHT * (i + IDX));

      JProgressBar bar = new JProgressBar();
      bar.setSize(400, BAR_HEIGHT);
      bar.setLocation(BUTTON_HEIGHT * 2 + 150, (BUTTON_HEIGHT + BAR_HEIGHT) * IDX + (2 * (BAR_HEIGHT * i)));

      Progress progress = new Progress(bar, button, DELAY);

      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JButton button = (JButton) e.getSource();
          int idx = Integer.parseInt(button.getActionCommand());
          boolean isReverted = idx % 2 != 0;

          progress.setRevert(isReverted);

          Thread thread = new Thread(progress);
          thread.start();
        }
      });

      frame.add(button);
      frame.add(bar);
    }

    frame.setVisible(true);
  }
}
