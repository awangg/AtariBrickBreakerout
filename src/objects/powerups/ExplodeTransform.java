package objects.powerups;

import objects.tiles.*;
import client.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class ExplodeTransform extends Powerup {

    private BufferedImage img;

    public ExplodeTransform(int x, int y, int w, int h) {
        super(x, y, w, h);
        try {
            img = ImageIO.read(Main.buildImageFile("explpowerup.png"));
        } catch (Exception e) {
            System.out.println("Image Not Found");
        }
    }

    @Override
    public void action() {
        for(int r = 0; r < Panel.board.length; r++) {
            for(int c = 0; c < Panel.board[r].length; c++) {
                if(Panel.board[r][c] != null && !Panel.board[r][c].isExploding()) {
                    if(Math.random() > .9) {
                        Block bloc = Panel.board[r][c];
                        Panel.board[r][c] = new Exploding(bloc.getPosition().x, bloc.getPosition().y, bloc.getWidth(), bloc.getHeight());
                    }
                }
            }
        }
    }

    @Override
    public void display(Graphics2D g2) {
        g2.drawImage(img, getPosition().x, getPosition().y, getWidth(), getHeight(), null);
    }
}
