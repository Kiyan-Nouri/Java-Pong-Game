import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PongGamePrototype extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int PADDLE_WIDTH = 10;
    private final int PADDLE_HEIGHT = 100;
    private final int BALL_DIAMETER = 20;
    private int ballX = WIDTH / 2 - BALL_DIAMETER / 2;
    private int ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int ballXVel = -2;
    private int ballYVel = 1;
    private int score1 = 0;
    private int score2 = 0;
    private boolean gameStarted = false;

    public PongGamePrototype() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        Timer timer = new Timer(5, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw paddles
        g.setColor(Color.BLACK);
        g.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);

        // Draw scores
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString(String.valueOf(score1), WIDTH / 2 - 50, 50);
        g.drawString(String.valueOf(score2), WIDTH / 2 + 25, 50);

        if (!gameStarted) {
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press SPACE to start", WIDTH / 2 - 100, HEIGHT / 2 - 20);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (gameStarted) {
            // Move ball
            ballX += ballXVel;
            ballY += ballYVel;

            // Check for collision with paddles
            if (ballX <= PADDLE_WIDTH && ballY + BALL_DIAMETER >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
                ballXVel = -ballXVel;
            } else if (ballX + BALL_DIAMETER >= WIDTH - PADDLE_WIDTH && ballY + BALL_DIAMETER >= paddle2Y
                    && ballY <= paddle2Y + PADDLE_HEIGHT) {
                ballXVel = -ballXVel;
            }

            // Check for collision with walls
            if (ballY <= 0 || ballY + BALL_DIAMETER >= HEIGHT) {
                ballYVel = -ballYVel;
            }

            // Check for scoring
            if (ballX <= 0) {
                score2++;
                reset();
            } else if (ballX + BALL_DIAMETER >= WIDTH) {
                score1++;
                reset();
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddle2Y -= 10;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2Y += 10;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameStarted = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            paddle1Y -= 10;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            paddle1Y += 10;
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    private void reset() {
        ballX = WIDTH / 2 - BALL_DIAMETER / 2;
        ballY = HEIGHT / 2 - BALL_DIAMETER / 2;
        ballXVel = -ballXVel;
        paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        gameStarted = false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PongGamePrototype game = new PongGamePrototype();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
