package objects.powerups;

import client.Main;
import client.Panel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;

public class SpeedPaddle extends Powerup {

    private BufferedImage img;

    public SpeedPaddle(int x, int y, int w, int h) {
        super(x, y, w, h);
        try {
            img = ImageIO.read(Main.buildImageFile("speedpowerup.png"));
        } catch (Exception e) {
            System.out.println("Image Not Found");
        }
    }

    @Override
    public void action() {
        if(Panel.player.getSpeed() < 20) {
            Panel.player.setSpeed((int) (Panel.player.getSpeed() * 1.5));
        }
    }

    @Override
    public void display(Graphics2D g2) {
        g2.drawImage(img, getPosition().x, getPosition().y, getWidth(), getHeight(), null);
    }
}
