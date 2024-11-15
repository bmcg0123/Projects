import java.awt.*;

public class Ball {
    private int x, y, diameter, xVelocity, yVelocity;

    public Ball(int x, int y, int diameter, int xVelocity, int yVelocity) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void reverseX() {
        xVelocity = -xVelocity;
    }

    public void reverseY() {
        yVelocity = -yVelocity;
    }

    public void setXDirection(int dx) {
        xVelocity = dx;
    }

    public void setYDirection(int dy) {
        yVelocity = dy;
    }

    public void setDirection(int dx, int dy) {
        xVelocity = dx;
        yVelocity = dy;
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public void draw(Graphics g) {
        g.fillOval(x, y, diameter, diameter);
    }
}

