package objects;

import java.awt.*;
import client.*;

public class Paddle {

    private int x, y, w, h, speed;
    private Color color;

    public Paddle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        speed = 15;
        color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
    }

    public void move(String dir) {
        if(dir.equals("left") && x >= 0) {
            x -= speed;
        }else if(dir.equals("right") && x + w <= Main.WIDTH) {
            x += speed;
        }
    }

    public void display(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.GREEN);
        g2.drawRect(x, y, w, h);
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void setPosition(Point p) {
        x = p.x;
        y = p.y;
    }

    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }
}
