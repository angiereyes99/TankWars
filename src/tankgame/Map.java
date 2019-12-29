package tankgame;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;


import static javax.imageio.ImageIO.read;

public class Map  {

    public static final int GAME_WIDTH = 1600;
    public static final int GAME_HEIGHT = 1000;
    public static ArrayList<GameObjects> objects = new ArrayList<>();
    private BufferedImage background, wall, wall2, healthPowerUp, increasedDamage, speedUp;

    Map(){

    }

    public void init() {

        try {

            background = read(new File("resources/Background.bmp"));
            wall = read(new File("resources/Wall1.gif"));
            wall2 = read(new File("resources/Wall2.gif"));
            healthPowerUp = read(new File("resources/Bouncing.gif"));
            increasedDamage = read(new File("resources/Weapon.gif"));
            speedUp = read(new File("resources/Explosion_small.gif"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

         for(int t = 10; t < 300; t+=30){ // breakable  top
             objects.add(new BreakableWall(wall2, 780, t));
         }
         for (int a = 600; a <990; a +=30){// breakable middle top
             objects.add(new BreakableWall(wall2, a, 300));
         }
         for (int z = 600; z < 840; z +=120){ //middle top
             objects.add(new UnbreakableWall(wall, z, 300));
         }
         for (int b = 600; b < 990; b +=30){ // breakable middle bottom
             objects.add(new BreakableWall(wall2, b, 600));
         }
         for (int y = 600; y < 1000; y +=120){ //middle bottom
             objects.add(new UnbreakableWall(wall, y, 600));
         }
         for(int t = 630; t < 970; t+=30){ // breakable  bottom
             objects.add(new BreakableWall(wall2, 780, t));
         }
         for(int h = 30; h < 600; h+=30){ // breakable  left
             objects.add(new UnbreakableWall(wall, h, 450));
         }
         for (int c = 300; c < 590; c +=30){ //breakable middle left
             objects.add(new BreakableWall(wall2, 600, c));
         }
         for (int w = 300; w < 600; w +=150){ //middle left
             objects.add(new UnbreakableWall(wall, 600, w));
         }
         for(int h = 960; h < 1550; h+=30){ // breakable  right
             objects.add(new UnbreakableWall(wall, h, 450));
         }
         for (int v = 300; v < 590; v +=30){ //breakable middle right
             objects.add(new BreakableWall(wall2, 960, v));
         }
         for (int v = 300; v < 600; v +=150){ //middle right
             objects.add(new UnbreakableWall(wall, 960, v));
         }
         // wall border
         for(int i = 0; i < 1600; i+=30) { //wall top
             objects.add(new UnbreakableWall(wall, i, 10));
         }
         for(int j = 10; j < 1000; j+=30){ // wall left
                objects.add(new UnbreakableWall(wall, 0, j));
         }
         for(int k = 0; k < 1600; k+=30) { //wall bottom
             objects.add(new UnbreakableWall(wall, k,970));
         }
         for(int jk = 10; jk < 1000; jk +=30){ // wall right
             objects.add(new UnbreakableWall(wall, 1560, jk));
         }

        objects.add(new HealthPowerUp(healthPowerUp, 774, 448));
        objects.add(new PlusOneLifePowerUp(increasedDamage, 65, 900));
        objects.add(new SpeedUpPowerUp(speedUp, 1446, 87));
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
       for (int i = 0; i < GAME_WIDTH/background.getWidth()+1; i++){
           for (int j = 0; j <GAME_HEIGHT/background.getHeight()+1; j++){
               g2d.drawImage(background, i*background.getWidth(), j*background.getHeight(),null);
           }
       }
       for(int i = 0; i < objects.size(); i++){
           objects.get(i).drawImage(g2d);
       }
    }
}