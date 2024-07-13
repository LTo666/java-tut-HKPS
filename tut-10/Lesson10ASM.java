import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;

class Ball extends JLabel {
  public int CoordX = 0;
  private final int COORD_Y;
  public static final int DIAMETER = 50;
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
    g.fillOval(CoordX, COORD_Y, DIAMETER, DIAMETER);
  }
}

class Bounce implements Runnable {
  private final Ball BALL;
  public static int MaxBounceWidth;
  private final int DELAY_INTERVAL;

  public Bounce(Ball ball, int delayInterval) {
    this.BALL = ball;
    this.DELAY_INTERVAL = delayInterval > 0 ? delayInterval : 1;
  }

  public void run() {
    while (true) {
      for (int i = 0; i < MaxBounceWidth - Ball.DIAMETER; i++) {
        BALL.CoordX = i;
        BALL.repaint();

        try {
          Thread.sleep(DELAY_INTERVAL);
        } catch (InterruptedException e) {
        }
      }

      for (int i = MaxBounceWidth - Ball.DIAMETER; i > 0; i--) {
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
  // Some settings
  private static final int DELAY_INTERVAL = 1;
  private static final int MAX_FRAME_WIDTH = 600;
  private static int NoOfBalls = 14;

  public static void main(String[] args) {
    final int MAX_NO_OF_BALLS = MAX_FRAME_WIDTH / Ball.DIAMETER;

    if (NoOfBalls > MAX_NO_OF_BALLS) {
      NoOfBalls = MAX_NO_OF_BALLS;
    }

    final int MAX_HEIGHT_FOR_BALL_BOUNCING = MAX_FRAME_WIDTH / NoOfBalls;
    Ball.LabelWidth = MAX_FRAME_WIDTH;
    Ball.LabelHeight = MAX_HEIGHT_FOR_BALL_BOUNCING;
    Bounce.MaxBounceWidth = MAX_FRAME_WIDTH;

    JFrame frame = new JFrame("Bouncing Balls");
    frame.setSize(MAX_FRAME_WIDTH, MAX_FRAME_WIDTH);
    frame.setLayout(null);

    for (int i = 0; i < NoOfBalls; i++) {
      Ball ball = new Ball(i * MAX_HEIGHT_FOR_BALL_BOUNCING);
      ball.setSize(MAX_FRAME_WIDTH, MAX_FRAME_WIDTH);
      frame.add(ball);

      Bounce bounce = new Bounce(ball, DELAY_INTERVAL * (i + 1));
      Thread thread = new Thread(bounce);
      thread.start();
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
