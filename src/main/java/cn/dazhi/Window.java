package cn.dazhi;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class Window extends Frame {
    public static final int GAME_WIDTH = 1000;  //  窗口宽
    public static final int GAME_HEIGHT = 700;  //  窗口高

    public static int count;
    public static int gameTime = 1;

    public String data;
    private Random rd = new Random();
    String[] letters = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    Image screenImage = null;

    public void launchFrame(){
        this.setTitle("打字练习");
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
        this.addKeyListener(new KeyMonitor());
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
        gScreen.setColor(c);
        paint(gScreen);
        g.drawImage(screenImage,0,0,null);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.drawString("总计："+count,20,40);
        g.drawString("时间："+gameTime+"s",20,60);
        g.drawString("字数/分钟："+count*60/gameTime,20,80);
        if (null==data) {
            data = letters[rd.nextInt(26)];
            count++;
        }
        g.setColor(Color.gray);
        g.setFont(new Font(null,1,400));
        g.drawString(""+ data,300,500);
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (String.valueOf(e.getKeyChar()).equals(data)){
                data=null;
            }
        }
    }

    public static void main( String[] args )
    {
        Window win = new Window();
        win.launchFrame();
    }
}
