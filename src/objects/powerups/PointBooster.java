package objects.powerups;

import client.Main;
import client.Panel;

import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PointBooster extends Powerup {

    private BufferedImage img;

    public PointBooster(int x, int y, int w, int h) {
        super(x, y, w, h);
        try {
            img = ImageIO.read(Main.buildImageFile("pointpowerup.png"));
        } catch (Exception e) {
            System.out.println("Image Not Found");
        }
    }

    @Override
    public void action() {
        Panel.points += 150;
    }

    @Override
    public void display(Graphics2D g2) {
        g2.drawImage(img, getPosition().x, getPosition().y, getWidth(), getHeight(), null);
    }
}
