package client;

import javax.swing.*;
import java.net.URL;

public class Main {
    private static Panel p;
    public static final int WIDTH = 600, HEIGHT = 500, NAMEBAR = 22;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT);

        p = new Panel();
        frame.add(p);
        p.setLayout(null);
        p.setFocusable(true);
        p.grabFocus();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
    }

    public static URL buildImageFile(String file){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(file);
    }
}

/*
TODO

Images Cited

Ball Powerup (ballpowerup.png): https://openclipart.org/image/2400px/svg_to_png/292096/rainbow_sphere.png
Explosion (explode.png): https://images.clipartuse.com/d333c602bd834fa0e29a8b8a6f4e5c56_6-starburst-explosion-comic-vector-png-transparent-svg-_1024-850.png
Explosion Powerup (explpowerup.png): http://jacksbox.de/wp-content/uploads/2013/03/box_yellow_em.png
Fire (fire.png): http://clipart-library.com/image_gallery2/Fire-PNG.png
Point Powerup (pointpowerup.png): https://www.shareicon.net/data/128x128/2016/10/11/842215_arrows_512x512.png
Sparkle (sparkle.png): https://pngtree.com/freepng/decorative-white-sparkling-effect_187405.html
Lives Powerup (speedpowerup.png): https://image.flaticon.com/icons/svg/248/248610.svg

TODO
*/
