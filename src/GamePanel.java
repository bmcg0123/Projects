import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private Timer timer;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks;
    private boolean gameRunning = true;

    public GamePanel() {
        setPreferredSize(new Dimension(700, 500));
        setBackground(Color.WHITE);

        paddle = new Paddle(300, 450, 100, 10);
        ball = new Ball(340, 430, 20, 2, -3);

        bricks = new ArrayList<>();
        initializeBricks();

        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(15, this);
    }

    private void initializeBricks() {
        int brickWidth = 60;
        int brickHeight = 20;
        int padding = 5;
        int rows = 2;
        int cols =10;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * (brickWidth + padding) + 30;
                int y = i * (brickHeight + padding) + 50;
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }
    }

    public void startGame() {
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw paddle
        g.setColor(Color.GREEN);
        paddle.draw(g);

        // Draw ball
        g.setColor(Color.BLACK);
        ball.draw(g);

        // Draw bricks
        g.setColor(Color.RED);
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                brick.draw(g);
            }
        }

        if (!gameRunning) {
            drawGameOver(g);
        }
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String message = bricks.isEmpty() ? "You Win! Press R to Restart" : "Game Over! Press R to Restart";
        g.drawString(message, getWidth() / 2 - 150, getHeight() / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            ball.move();

            // Check paddle collision
            if (ball.getBounds().intersects(paddle.getBounds())) {
                ball.setYDirection(-Math.abs(ball.getYVelocity()));

                // Adjust x-direction based on where the ball hits the paddle
                int paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                int ballCenter = ball.getX() + ball.getDiameter() / 2;
                int offset = ballCenter - paddleCenter;

                ball.setXDirection(offset / 10); // Adds spin effect
            }

            // Check wall collisions
            if (ball.getX() <= 0 || ball.getX() >= getWidth() - ball.getDiameter()) {
                ball.reverseX();
            }
            if (ball.getY() <= 0) {
                ball.reverseY();
            }

            // Check if ball falls below paddle
            if (ball.getY() >= getHeight()) {
                gameRunning = false;
            }

            // Check brick collisions
            for (Brick brick : bricks) {
                if (!brick.isDestroyed() && ball.getBounds().intersects(brick.getBounds())) {
                    brick.destroy();
                    ball.reverseY();
                    break;
                }
            }

            // Check win condition
            if (bricks.stream().allMatch(Brick::isDestroyed)) {
                gameRunning = false;
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT) {
            paddle.moveRight();
        }
        if (key == KeyEvent.VK_R && !gameRunning) {
            resetGame();
        }
    }

    private void resetGame() {
        ball.setPosition(340, 430);
        ball.setDirection(2, -3);
        paddle.setPosition(300);
        bricks.clear();
        initializeBricks();
        gameRunning = true;
        timer.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}

