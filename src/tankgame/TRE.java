package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

public class TRE extends JPanel  {

    public static final int GAME_WIDTH = 1600;
    public static final int GAME_HEIGHT = 1000;
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 750;

    private BufferedImage world;
    private Graphics2D buffer;

    private JFrame jf;
    private Tank t1;
    private Tank t2;
    private Map background;

    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {
            while (true) {
                trex.t1.update();
                trex.t2.update();
                trex.repaint();
                System.out.println(trex.t1);
                System.out.println(trex.t2);
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.jf = new JFrame("Tank Rotation");
        this.world = new BufferedImage(TRE.GAME_WIDTH, TRE.GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            t1img = read(new File("resources/tank1.png"));
            t2img = read(new File("resources/tank1.png"));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        t1 = new Tank(1160, 868, 0, 0, 0, t1img);
        t2 = new Tank(220,289,0,0,0,t2img);

        background= new Map();
        background.init();

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);

        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);

        this.background.drawImage(buffer);

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);

        g2.drawImage(world,0,0,null);

        int boundsX = checkBoundsX(t1);
        int boundsY = checkBoundsY(t1);
        int boundsX2 = checkBoundsX(t2);
        int boundsY2 = checkBoundsY(t2);

        BufferedImage leftScreen = this.world.getSubimage(boundsX2, boundsY2, SCREEN_WIDTH/2, SCREEN_HEIGHT);
        BufferedImage rightScreen = this.world.getSubimage(boundsX, boundsY, SCREEN_WIDTH/2, SCREEN_HEIGHT);

        g2.drawImage(leftScreen, 0,0,null);
        g2.drawImage(rightScreen, SCREEN_WIDTH/2, 0, null);

        AffineTransform minimap = AffineTransform.getTranslateInstance(SCREEN_WIDTH/2.5, 0);
        minimap.scale(0.17,0.17);
        g2.drawImage(world, minimap, null);

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t1.getLives(), SCREEN_WIDTH * 65/80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 2 Health: " + this.t1.getHealth(), SCREEN_WIDTH * 50 / 80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t1.getHealth() && i < 100; i++) {
            g2.drawRect(SCREEN_WIDTH * 50 / 80 + i, SCREEN_HEIGHT * 36 / 40, 60, 30);
        }

        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t2.getLives(), SCREEN_WIDTH * 27/80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 1 Health: " + this.t2.getHealth(), SCREEN_WIDTH * 12 / 80, SCREEN_HEIGHT * 35 / 40);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t2.getHealth() && i < 100; i++) {
            g2.drawRect(SCREEN_WIDTH * 13 / 80 + i, SCREEN_HEIGHT * 36 / 40, 60, 30);
        }

        this.t1.isWon();
        this.t2.isWon();

    }

    public int checkBoundsX(Tank tank1){

        int x = tank1.getX() - SCREEN_WIDTH/4;

        if(x < 0){
            x = 0;

        }else if(x > GAME_WIDTH - SCREEN_WIDTH/2){
            x = GAME_WIDTH - SCREEN_WIDTH/2;
        }

        return x;
    }

    public int checkBoundsY(Tank tank2){

        int x = tank2.getY();

        if(x < 0){
            x = 0;

        }else if(x > GAME_HEIGHT - SCREEN_HEIGHT){
            x = GAME_HEIGHT - SCREEN_HEIGHT;
        }

        return x;
    }
}