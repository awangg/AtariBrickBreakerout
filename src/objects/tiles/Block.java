package objects.tiles;

import java.awt.*;

public class Block {

    private int x, y, w, h, row, col;
    private boolean magic, exploding, onFire;
    private Color color;

    public Block(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        row = this.y / this.h;
        col = this.x / this.w;
        color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
        magic = false;
        exploding = false;
        onFire = false;
    }

    public void display(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, w, h);
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public boolean isMagic() {
        return magic;
    }

    public boolean isExploding() {
        return exploding;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public Point getCoordinates() {
        return new Point(row-1, col);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void setMagic(boolean b) {
        magic = b;
    }

    public void setExploding(boolean b) {
        exploding = b;
    }

    public void setOnFire(boolean b) {
        onFire = b;
    }
}
