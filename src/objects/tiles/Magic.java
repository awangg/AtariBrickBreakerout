package objects.tiles;

import java.awt.*;
import objects.powerups.*;

public class Magic extends Block {

    private Powerup drop;

    public Magic(int x, int y, int w, int h) {
        super(x, y, w, h);
        setMagic(true);
        determinePowerup();
    }

    public void determinePowerup() {
        int x = getPosition().x;
        int y = getPosition().y;
        int w = getWidth();
        int h = getHeight();

        Powerup[] powerups = new Powerup[] {new ExplodeTransform(x + w/2, y + h/2, 20, 20), new MoreBalls(x + w/2, y + h/2, 20, 20), new PointBooster(x + w/2, y + h/2, 20, 20), new SpeedPaddle(x + w/2, y + h/2, 20, 20)};
        drop = powerups[(int)(Math.random() * powerups.length)];
    }

    public Powerup spawnPowerup() {
        return drop;
    }

    public void display(Graphics2D g2) {
        super.display(g2);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.drawString("?", getPosition().x + getWidth()/2 - g2.getFontMetrics().stringWidth("?")/2, getPosition().y + getHeight()/2 + 10);
    }
}
