package cn.dazhi;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Turntable extends Frame {
    public static final int GAME_WIDTH = 1000;  //  窗口宽
    public static final int GAME_HEIGHT = 700;  //  窗口高

    public boolean over = false;

    public static int count;
    public static int gameTime = 1;

    Random rd = new Random();
    public boolean start;

    public int temp;//用了计数，标记循环多少次换一个奖项
    TurntableBG turn = null;

    private ArrayList<TurntableBG> turntableBG = new ArrayList<>();
    {
        turntableBG.add(new TurntableBG(300,200,"500元"));
        turntableBG.add(new TurntableBG(400,200,"5000元"));
        turntableBG.add(new TurntableBG(500,200,"5万元"));
        turntableBG.add(new TurntableBG(300,300,"50万元"));
        turntableBG.add(new TurntableBG(400,300,"500万元"));
        turntableBG.add(new TurntableBG(500,300,"5000万元"));
        turntableBG.add(new TurntableBG(300,400,"5亿元"));
        turntableBG.add(new TurntableBG(400,400,"50亿元"));
        turntableBG.add(new TurntableBG(500,400,"500亿元"));
    }

    Image screenImage = null;

    public void launchFrame(){
        this.setTitle("幸运大转盘");
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
        this.addKeyListener(new Turntable.KeyMonitor());
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
//        g.drawString("总计："+count,20,40);
        g.drawString("时间："+gameTime+"s",20,60);

        if (over){
            g.setFont(new Font("宋体",Font.BOLD,40));
            g.drawString("游戏结束,请按r键重新开始",300,300);
        }
        for (TurntableBG turntable : turntableBG){
            turntable.draw(g);
        }

        if (start){
            if (count<100) {
                if (temp > 10) {
                    turn = turntableBG.get(rd.nextInt(9));
                    temp = 0;
                } else {
                    temp++;
                    count++;
                }
            }else{
                g.setColor(Color.green);
                g.setFont(new Font("宋体",Font.BOLD,40));
                g.drawString("恭喜你，中了"+turn.data,300,150);
            }
            if (null == turn) turn = turntableBG.get(rd.nextInt(9));
            g.setColor(Color.white);
            g.fillRect(turn.x+25,turn.y+25,50,50);
        }
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            start = true;
            count = 0;
        }
    }

    private class TurntableBG{
        private int x;
        private int y;
        private String data;

        public TurntableBG(int x, int y, String data) {
            this.x = x;
            this.y = y;
            this.data = data;
        }

        Color color = randomColor();
        public void draw(Graphics g){
            g.setColor(color);
            g.fillRect(x,y,100,100);
            g.setColor(Color.black);
            g.drawString(data,x+25,y+25);
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
        Turntable turntable = new Turntable();
        turntable.launchFrame();
    }
}
