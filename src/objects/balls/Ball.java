package objects.balls;

import client.*;
import java.awt.*;

public class Ball {

    private double x, y, r;
    private double vx, vy;
    private Color color;
    private boolean hitBottom = false;
    private Point[] points;

    public Ball(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
        vx = (int)(Math.random() * 5) + 2;
        vy = (int)(Math.random() * 5) + 2;
        if(Math.random() > .5) {
            vx *= -1;
        }
        if(Math.random() > .5) {
            vy *= -1;
        }
    }

    public void move() {
        x += vx;
        y += vy;

        if(x - vx < 0) {
            vx *= -1.05;
            x = vx;
        }else if(x + r/2 + vx > Main.WIDTH) {
            vx *= -1.05;
            x = Main.WIDTH - vx - r/2 - 1;
        }

        if(y - vy < 0) {
            vy *= -1.05;
            y = vy;
        }else if(y + r/2 + vy > Main.HEIGHT - Main.NAMEBAR) {
            vx = 0;
            vy = 0;
            y = Main.HEIGHT - Main.NAMEBAR - r/2 - vy;
            hitBottom = true;
        }
    }

    public void setPoints() {
        double[] deltaX = new double[8];
        double[] deltaY = new double[8];
        points = new Point[8];
        for(int i = 0; i < 360; i+=45) {
            int index = i/45;

            deltaX[index] = Math.cos(Math.toRadians(i));
            deltaY[index] = Math.sin(Math.toRadians(i));

            points[index] = new Point((int)(x + (int)(deltaX[index] * (r/2))), (int)(y + (int)(deltaY[index] * (r/2))));
        }
    }

    public boolean collide(Rectangle rect) {
        setPoints();
        for(Point p : points) {
            if(rect.contains(p)) {
                if(y + r/2 > (int)(rect.getY() + rect.getHeight())) {
                    if(vy < 0) vy *= -1.05;
                    y = (int)rect.getY() + (int)rect.getHeight() + r/2;
                    return true;
                }else if(y - r/2 < rect.getY()) {
                    if(vy > 0) vy *= -1.05;
                    y = (int)rect.getY() - r/2;
                    return true;
                }
                if(x + r/2 > rect.getX() + rect.getWidth()) {
                    if(vx < 0) vx *= -1.05;
                    x = (int)rect.getX() + (int)rect.getWidth() + r/2;
                    return true;
                }else if(x - r/2 < rect.getX()) {
                    if(vx > 0) vx *= -1.05;
                    x = (int)rect.getX() - r/2;
                    return true;
                }
            }
        }
        return false;
    }

    public void display(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(x - r/2), (int)(y - r/2), (int)r, (int)r);
        g2.setColor(Color.RED);
        g2.drawOval((int)(x - r/2), (int)(y - r/2), (int)r, (int)r);
    }

    public Point getPosition() {
        return new Point((int)x, (int)y);
    }

    public double getRadius() {
        return r;
    }

    public boolean hasHitBottom() {
        return hitBottom;
    }

    public void setPosition(Point pos) {
        x = pos.x;
        y = pos.y;
    }
}
