import java.awt.*;

public class Paddle {
    private int x, y, width, height;
    private int speed = 10;

    public Paddle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void setPosition(int newX) {
        x = newX;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }
}

