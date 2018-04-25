package objects.tiles;

import client.Panel;
import graphics.*;

import java.awt.*;

public class Exploding extends Block {

    public Exploding(int x, int y, int w, int h) {
        super(x, y, w, h);
        setExploding(true);
    }

    // Returns new 2D block array that has destroyed blocks around it
    public Block[][] explode(Block[][] original) {
        int row = getCoordinates().x;
        int col = getCoordinates().y;

        int[] deltaX = new int[] {-1, 0, 1};
        int[][] deltaY = new int[][] {
                {-1, 0, 1},
                {-1, 1},
                {-1, 0, 1}
        };

        for(int dx = 0; dx < deltaX.length; dx++) {
            for(int dy = 0 ; dy < deltaY[dx].length; dy++) {
                int x = col + deltaX[dx];
                int y = row + deltaY[dx][dy];

                if(y >= 0 && y < original.length && x >= 0 && x < original[y].length && Math.random() > .5 && original[y][x] != null) {
                    Block bloc = original[y][x];
                    if(bloc.isExploding()) {
                        Exploding expl = (Exploding)bloc;
                        Panel.effects.add(new Explosion(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, getWidth() * 3, getHeight() * 3, Panel.currentTime));
                        original = expl.explode(original);
                    }else if(bloc.isMagic()) {
                        Magic mag = (Magic)bloc;
                        Panel.effects.add(new Sparkle(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, (int)(getWidth() * 1.15), getHeight() * 2, Panel.currentTime));
                        Panel.powerups.add(mag.spawnPowerup());
                    }
                    original[y][x] = null;
                }else if(y >= 0 && y < original.length && x >= 0 && x < original[y].length && original[y][x] != null && Math.random() > .5) {
                    Block bloc = original[y][x];
                    original[y][x].setOnFire(true);
                    Panel.effects.add(new Fire(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, getWidth(), getHeight(), Panel.currentTime));
                }
            }
        }

        return original;
    }

    public void display(Graphics2D g2) {
        super.display(g2);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.drawString("!!!", getPosition().x + getWidth()/2 - g2.getFontMetrics().stringWidth("!!!")/2, getPosition().y + getHeight()/2 + 10);
    }
}
