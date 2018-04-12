package objects.powerups;

import client.Main;
import client.Panel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;

public class LifeUp extends Powerup {

    private BufferedImage img;

    public LifeUp(int x, int y, int w, int h) {
        super(x, y, w, h);
        try {
            img = ImageIO.read(Main.buildImageFile("speedpowerup.png"));
        } catch (Exception e) {
            System.out.println("Image Not Found");
        }
    }

    @Override
    public void action() {
        Panel.lives+=2;
    }

    @Override
    public void display(Graphics2D g2) {
        g2.drawImage(img, getPosition().x, getPosition().y, getWidth(), getHeight(), null);
    }
}
