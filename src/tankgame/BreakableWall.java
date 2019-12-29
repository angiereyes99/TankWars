package tankgame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObjects {

    private Rectangle bound;

    BreakableWall(BufferedImage img, int x, int y) {
        super(x,y,img);
        this.bound = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
        gameObjects.add(this);
    }
}