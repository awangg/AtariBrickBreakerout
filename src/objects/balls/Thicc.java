package objects.balls;

import client.*;
import client.Panel;
import java.awt.*;

public class Thicc extends Ball {

    private int killCount;

    public Thicc(int x, int y, int r) {
        super(x, y, r);
        killCount = 0;
    }

    @Override
    public void move() {
        super.move();

        int x = getPosition().x;
        int y = getPosition().y;
        int r = (int)getRadius();
        int vx = getVelocity().x;
        int vy = getVelocity().y;

        if(x - r/2 - vx <= 0 || x + r/2 + vx >= Main.WIDTH || y - r/2 - vy <= 0 || y + r/2 + vy >= Main.HEIGHT - Main.NAMEBAR || killCount >= 10) {
            Panel.balls.add(new Ball(Main.WIDTH / 2, Main.HEIGHT - 150, Panel.ballRadius));
            Panel.balls.remove(this);
        }

        setPoints();
    }

    @Override
    public boolean collide(Rectangle rect) {
        for(Point p : getPoints()) {
            if(rect.contains(p)) {
                killCount++;
                return true;
            }
        }
        return false;
    }
}
