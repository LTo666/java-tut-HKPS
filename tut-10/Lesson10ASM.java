import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Ball extends JLabel {
  public int CoordX = 0;
  private final int COORD_Y;
  public static int TwoRadius = 50;
  public static int LabelWidth = 1000;
  public static int LabelHeight = 1000;

  public Ball(int coordY) {
    this.COORD_Y = coordY;
  }

  public void paint(Graphics g) {
    // Clear the previous ball
    g.setColor(Color.WHITE);
    g.fillRect(0, COORD_Y, LabelWidth, LabelHeight);

    g.setColor(Color.RED);
    g.fillOval(CoordX, COORD_Y, TwoRadius, TwoRadius);
  }
}

class Bounce implements Runnable {
  private final Ball BALL;
  public static int MAX_FRAME_WIDTH;
  private int DELAY_INTERVAL = 1;

  public Bounce(Ball ball, int DELAY_INTERVAL) {
    this.BALL = ball;
    if (DELAY_INTERVAL > 0) {
      this.DELAY_INTERVAL = DELAY_INTERVAL;
    }
  }

  public void run() {
    while (true) {
      for (int i = 0; i < MAX_FRAME_WIDTH - Ball.TwoRadius; i++) {
        BALL.CoordX = i;
        BALL.repaint();

        try {
          Thread.sleep(DELAY_INTERVAL);
        } catch (InterruptedException e) {
        }
      }

      for (int i = MAX_FRAME_WIDTH - Ball.TwoRadius; i > 0; i--) {
        BALL.CoordX = i;
        BALL.repaint();

        try {
          Thread.sleep(DELAY_INTERVAL);
        } catch (InterruptedException e) {
        }
      }
    }
  }

}

public class Lesson10ASM {
  private static final int DELAY_INTERVAL = 1;
  private static final int MAX_FRAME_WIDTH = 600;
  private static final int NO_OF_BALLS = 12;

  public static void main(String[] args) {
    final int MAX_HEIGHT_FOR_BALL = MAX_FRAME_WIDTH / NO_OF_BALLS;
    Ball.LabelWidth = MAX_FRAME_WIDTH;
    Ball.LabelHeight = MAX_HEIGHT_FOR_BALL;

    JFrame frame = new JFrame("Bouncing Balls");
    frame.setSize(MAX_FRAME_WIDTH, MAX_FRAME_WIDTH);
    frame.setLayout(null);

    for (int i = 0; i < NO_OF_BALLS; i++) {
      Ball ball = new Ball(i * MAX_HEIGHT_FOR_BALL);
      ball.setLocation(0, 0);
      ball.setSize(MAX_FRAME_WIDTH, MAX_FRAME_WIDTH);
      frame.add(ball);

      Bounce.MAX_FRAME_WIDTH = MAX_FRAME_WIDTH;
      Bounce bounce = new Bounce(ball, DELAY_INTERVAL * (i + 1));
      Thread thread = new Thread(bounce);
      thread.start();
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
