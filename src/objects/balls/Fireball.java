package objects.balls;

import client.Main;
import client.Panel;
import java.awt.*;

public class Fireball extends Ball {

    public Fireball(int x, int y, int r) {
        super(x, y, r);
        setFireball(true);
    }

    @Override
    public boolean collide(Rectangle rect) {
        for(Point p : getPoints()) {
            if(rect.contains(p)) {
                Panel.balls.add(new Ball(Main.WIDTH / 2, Main.HEIGHT - 150, Panel.ballRadius));
                Panel.balls.remove(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public void display(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fillOval((int)(getPosition().x - getRadius()/2), (int)(getPosition().y - getRadius()/2), (int)getRadius(), (int)getRadius());
        g2.setColor(Color.ORANGE);
        g2.fillOval((int)(getPosition().x - getRadius()/2), (int)(getPosition().y - getRadius()/2), (int)getRadius(), (int)getRadius());
    }
}
