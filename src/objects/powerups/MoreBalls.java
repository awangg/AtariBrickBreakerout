package objects.powerups;

import client.*;
import objects.balls.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class MoreBalls extends Powerup {

    private BufferedImage img;
    public MoreBalls(int x, int y, int w, int h) {
        super(x, y, w, h);
        try {
            img = ImageIO.read(Main.buildImageFile("ballpowerup.png"));
        } catch (Exception e) {
            System.out.println("Image Not Found");
        }
    }

    @Override
    public void action() {
        int numAdd = (int)(Math.random() * 3) + 1;
        for(int i = 0; i < numAdd; i++) {
            Panel.balls.add(new Ball(Main.WIDTH / 2, Main.HEIGHT - 150, (int)(Math.random() * 10) + 10));
        }
    }

    @Override
    public void display(Graphics2D g2) {
        g2.drawImage(img, getPosition().x, getPosition().y, getWidth(), getHeight(), null);
    }

}
