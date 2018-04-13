package objects.balls;

import client.*;
import java.awt.*;

public class Ball {

    private double x, y, r;
    private double vx, vy;
    private Color color;
    private boolean hitBottom = false, isFireball = false;
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
            if(Math.abs(vx) <= 15) {
                vx *= -1.05;
            }else {
                vx *= -1;
            }
            x = vx;
        }else if(x + r/2 + vx > Main.WIDTH) {
            if(Math.abs(vx) <= 15) {
                vx *= -1.05;
            }else {
                vx *= -1;
            }
            x = Main.WIDTH - vx - r/2 - 1;
        }

        if(y - vy < 0) {
            if(Math.abs(vy) <= 15) {
                vy *= -1.05;
            }else {
                vy *= -1;
            }
            y = vy;
        }else if(y + r/2 + vy > Main.HEIGHT - Main.NAMEBAR) {
            vx = 0;
            vy = 0;
            y = Main.HEIGHT - Main.NAMEBAR - r/2 - vy;
            hitBottom = true;
        }

        setPoints();
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
        boolean collided = false;
        for(Point p : points) {
            if(rect.contains(new Point((int)(p.x + vx), (int)(p.y + vy)))) {
                if(y > (int)(rect.getY() + rect.getHeight())) {
                    if(vy < 0 && Math.abs(vy) <= 15) {
                        vy *= -1.05;
                    }else if(vy < 0) {
                        vy *= -1;
                    }
                    y = rect.getY() + rect.getHeight() + r/2;
                    collided = true;
                }else if(y < rect.getY()) {
                    if(vy > 0 && Math.abs(vy) <= 15) {
                        vy *= -1.05;
                    }else if(vy > 0){
                        vy *= -1;
                    }
                    y = rect.getY() - r/2;
                    collided = true;
                }

                if(x > rect.getX() + rect.getWidth()) {
                    if(vx < 0 && Math.abs(vx) <= 15) {
                        vx *= -1.05;
                    }else if(vx < 0) {
                        vx *= -1;
                    }
                    x = rect.getX() + rect.getWidth() + r/2;
                    collided = true;
                }else if(x < rect.getX()) {
                    if(vx > 0 && Math.abs(vx) <= 15) {
                        vx *= -1.05;
                    }else if(vx > 0) {
                        vx *= -1;
                    }
                    x = rect.getX() - r/2;
                    collided = true;
                }
            }
        }
        return collided;
    }

    public void display(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(x - r/2), (int)(y - r/2), (int)r, (int)r);
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2));
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

    public Point getVelocity() {
        return new Point((int)vx, (int)vy);
    }

    public Point[] getPoints() {
        return points;
    }

    public boolean isFireball() {
        return isFireball;
    }

    public void setPosition(Point pos) {
        x = pos.x;
        y = pos.y;
    }

    public void setFireball(boolean b) {
        isFireball = b;
    }
}
