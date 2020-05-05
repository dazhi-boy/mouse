package cn.dazhi;

import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

//戳气球游戏
public class Balloon extends Frame {
    public static final int GAME_WIDTH = 1000;  //  窗口宽
    public static final int GAME_HEIGHT = 700;  //  窗口高

    public static int count;
    public static int gameTime = 1;
    public int temp;

    private Line line = new Line();
    private BalloonImg balloonImg;
    Image screenImage = null;

    public void launchFrame(){
        this.setTitle("戳气球");
        this.setLocation(100,10);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setBackground(Color.green);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.setResizable(false);
        this.addKeyListener(new Balloon.KeyMonitor());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    repaint();
                    try {
                        Thread.sleep(5);
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
        gScreen.setColor(c);
        paint(gScreen);
        g.drawImage(screenImage,0,0,null);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.drawString("总计："+count,20,40);
        g.drawString("时间："+gameTime+"s",20,60);
        if (null!=balloonImg){
            balloonImg.drow(g,this);
        }else{
            balloonImg = new BalloonImg();
            count++;
        }
        if (null != line&&null!=balloonImg){
            if (line.launch){
                line.x -=5;
            }
            line.drow(g);
            //碰撞检测（检测是否到了窗口外面，检测时候碰到了气球）
            if (400<balloonImg.x && balloonImg.x<500 && line.x<200){
                //替换一个爆炸的图片
                balloonImg.live = false;
                new PushMusic();
                line = null;
            }else if (line.x<100){
                line = null;
            }
        }else{
            line = new Line();
        }
    }
    //这是一个气球
    private class BalloonImg {
        private int x;
        private boolean live = true;
        private int i;
        private String direction = "right";
        Image img=Toolkit.getDefaultToolkit().getImage("D:\\learn\\snack\\image\\balloon.jpg");
        public void drow (Graphics g,Balloon balloon){
            g.drawImage(img,x, 100, 100, 100, balloon);
            move();
            if (!live){
                img=Toolkit.getDefaultToolkit().getImage("D:\\learn\\snack\\image\\bomb.jpg");
                i++;
            }
            if (i>50){
                balloon.balloonImg = null;
            }
        }

        private void move() {
            if ("right".equals(direction)){
                x ++;
                if (x+100>GAME_WIDTH){
                    direction = "left";
                }
            }else{
                x --;
                if (x<0){
                    direction = "right";
                }
            }
        }
    }
    //这是一根针
    private class Line {
        public boolean launch = false;
        private int x = 650;
        public void setX(int x){this.x = x;}

        public void drow (Graphics g){
            g.setColor(Color.red);
            g.drawLine(500,x,500,x+50);
        }
    }
    //回车键发射针去戳气球
    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar()=='\n' && null != line){
                line.launch = true;
            }
        }
    }
    //播放音乐
    private class PushMusic{
        AudioClip christmas = loadSound ("D:\\learn\\snack\\Music\\pushButtonMusic.wav");
        public PushMusic  ()
        {
            christmas.play ();
        }
        public AudioClip loadSound ( String filename ){
            URL url = null;
            try{
                url = new URL ("file:" + filename);
            }catch (MalformedURLException e){}
            return JApplet.newAudioClip (url);
        }
    }

    public static void main( String[] args )
    {
        Balloon balloon = new Balloon();
        balloon.launchFrame();
    }
}
