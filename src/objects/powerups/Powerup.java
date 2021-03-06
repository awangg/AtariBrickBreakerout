package objects.powerups;

import objects.*;
import java.awt.*;

public class Powerup {

    private int x, y, w, h, speed;

    public Powerup(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        speed = -13;
    }

    public void move() {
        y += speed;
        speed += 1;
    }

    // Checks if the powerup hits the paddle
    public boolean isCollected(Paddle p) {
        if(y + h >= p.getPosition().y && y + h <= p.getPosition().y + p.getHeight() && x + w >= p.getPosition().x && x <= p.getPosition().x + p.getWidth()) {
            return true;
        }
        return false;
    }

    // Runs whenever the powerup is collected; different for every powerup
    public void action() {
    }

    public void display(Graphics2D g2) {
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

    public void setPosition(Point p) {
        x = p.x;
        y = p.y;
    }
}
