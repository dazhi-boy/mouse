package cn.dazhi;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Box extends Frame {
    public static final int GAME_WIDTH = 1000;  //  窗口宽
    public static final int GAME_HEIGHT = 700;  //  窗口高

    public boolean over = false;

    public static int count;
    public static int gameTime = 1;

    public BoxImg boxImg;
    public ArrayList<BoxImg> boxList = new ArrayList<BoxImg>();

    Image screenImage = null;

    public void launchFrame(){
        this.setTitle("摆放盒子");
        this.setLocation(100,10);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setBackground(Color.black);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.setResizable(false);
        this.addKeyListener(new Box.KeyMonitor());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    gameTime++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void update(Graphics g) {
        if (screenImage==null){
            screenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gScreen = screenImage.getGraphics();
        Color c = gScreen.getColor();
        gScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        paint(gScreen);
        gScreen.setColor(c);
        g.drawImage(screenImage,0,0,null);


    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.drawString("总计："+count,20,40);
        g.drawString("时间："+gameTime+"s",20,60);

        if (over){
            g.setFont(new Font("宋体",Font.BOLD,40));
            g.drawString("游戏结束,请按r键重新开始",300,300);
        }

        if (null==boxImg){
            boxImg = new BoxImg();
            count++;
        }
        boxImg.draw(g);

        for (BoxImg box : boxList){
            box.draw(g);
        }

        //判断游戏是否结束
        if (boxList.size()>0)
        if (Math.abs(boxList.get(0).center-boxList.get(boxList.size()-1).center)>100){
            over = true;
        }
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (!over && e.getKeyChar()=='\n' && null!=boxImg){
                boxImg.dowm = true;
            }
            if (over && e.getKeyChar() == 'r'){
                count = 0;
                gameTime = 0;
                boxList = new ArrayList<>();
                over = false;
            }
        }
    }

    private class BoxImg{
        private int center;
        private int height;
        Color color = randomColor();
        private String direction = "right";
        private boolean dowm = false;
        private boolean live = true;

        public void draw(Graphics g){
            g.setColor(color);
            g.fillRect(center,height+100,200,50);
            if (live) move();
        }

        private void move() {
            if ("right".equals(direction)){
                center ++;
                if (center+100>GAME_WIDTH){
                    direction = "left";
                }
            }else{
                center --;
                if (center<0){
                    direction = "right";
                }
            }
            if (dowm) height+=5;
            if (height+300>GAME_HEIGHT) {
                live = false;
                for (BoxImg box : boxList){
                    box.height += 50;
                }
                boxList.add(this);
                boxImg = null;
                new HitMusic();
            }
        }

        public Color randomColor() {
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            return new Color(r, g, b);
        }
    }

    public static void main( String[] args )
    {
        Box box = new Box();
        box.launchFrame();
        new BgmMusic();
    }

    //播放音乐
    private static class BgmMusic{
        private static Media media = null;
        private static MediaPlayer mediaPlayer = null;
        public BgmMusic  (){
            File f = new File("D:\\learn\\snack\\Music\\bgm1.mp3");
            media = new Media(f.toURI().toString());
            //        必须有这一行，并且要在MediaPlayer创建之前
            final JFXPanel fxPanel = new JFXPanel();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    //播放音乐
    private static class HitMusic{
        private static Media media = null;
        private static MediaPlayer mediaPlayer = null;
        public HitMusic  () {
            File f = new File("D:\\learn\\snack\\Music\\hit.mp3");
            media = new Media(f.toURI().toString());
            //        必须有这一行，并且要在MediaPlayer创建之前
            final JFXPanel fxPanel = new JFXPanel();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        }
    }
}
