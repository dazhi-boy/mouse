package cn.dazhi;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

//老虎机游戏
public class Slots extends Frame {

    public static final int GAME_WIDTH = 1000;  //  窗口宽
    public static final int GAME_HEIGHT = 700;  //  窗口高

    public static int count = 0;
    public static int gameTime = 1;
    public boolean over = false;
    public Random random = new Random();
    public String result;

    public ArrayList<Card> cardList1 = new ArrayList<>();
    public ArrayList<Card> cardList2 = new ArrayList<>();
    public ArrayList<Card> cardList3 = new ArrayList<>();
    {
        cardList1.add(new Card("5万",200));
        cardList1.add(new Card("50万",200));
        cardList1.add(new Card("500万",200));
        cardList2.add(new Card("5万",300));
        cardList2.add(new Card("50万",300));
        cardList2.add(new Card("500万",300));
        cardList3.add(new Card("5万",400));
        cardList3.add(new Card("50万",400));
        cardList3.add(new Card("500万",400));
    }
    public Card card1,card2,card3;

    Image screenImage = null;

    public void launchFrame(){
        this.setTitle("老虎机");
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
        this.addKeyListener(new Slots.KeyMonitor());
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
            g.drawString(result,300,200);
        }

        if (count<200 && count%10 == 0) {
            card1 = cardList1.get(random.nextInt(3));
            card2 = cardList2.get(random.nextInt(3));
            card3 = cardList3.get(random.nextInt(3));
            count++;
        }else {
            count++;
        }
        if (count==200){
            //判断三个卡片数字是否一致
            if (card1.data.equals(card2.data)&&card2.data.equals(card3.data)){
                result = "恭喜你，中了"+card1.data;
            }else{
                result = "没中奖，回车再来一次";
            }
            over = true;
        }
        card1.draw(g);
        card2.draw(g);
        card3.draw(g);
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            over = false;
            count = 0;
        }
    }

    private class Card{
        private int x;
        private String data;

        public Card(String data,int x){
            this.data = data;
            this.x = x;
        }

        public void draw(Graphics g){
            g.setColor(Color.gray);
            g.fillRect(x,300,100,50);
            g.setColor(Color.red);
            g.drawRect(x,300,100,50);
            g.setColor(Color.blue);
            g.setFont(new Font("宋体",Font.BOLD,20));
            g.drawString(data,x+25,325);
        }
    }

    public static void main( String[] args )
    {
        Slots slots = new Slots();
        slots.launchFrame();
    }
}
