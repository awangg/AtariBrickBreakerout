package graphics;

import client.Main;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Effect {

    private int x, y, w, h;
    private BufferedImage img;
    private long birth, lifetime;

    public Effect(int x, int y, int w, int h, long birth, String filename) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.birth = birth;
        lifetime = 2000;

        try {
            img = ImageIO.read(Main.buildImageFile(filename));
        } catch (Exception e) {
            System.out.println("Image Not Found");
        }
    }

    public void display(Graphics2D g2) {
        g2.drawImage(img, x - w/2, y - h/2, w, h, null);
    }

    public long getBirth() {
        return birth;
    }

    public long getLifetime() {
        return lifetime;
    }

}
