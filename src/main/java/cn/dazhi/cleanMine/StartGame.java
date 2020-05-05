package cn.dazhi.cleanMine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class StartGame extends JPanel {
    public static final int GAME_WIDTH = 1000;  //  窗口宽
    public static final int GAME_HEIGHT = 700;  //  窗口高

    private int mine = 3;
    private int horizontal = 5;//横
    private int vertical = 5;//竖
    private Square[][] squares = new Square[vertical][horizontal];

    private Random rd = new Random();
    Image screenImage = null;
    private boolean over = false;

    public StartGame(){
        this.setBackground(Color.cyan);

        for(int i=0;i<vertical;i++){
            for (int j=0;j<horizontal;j++){
                squares[i][j] = new Square(i,j);
            }
        }
        //随机生成mine个地雷
        for (int i = 0;i<mine; i++){
            squares[rd.nextInt(vertical)][rd.nextInt(horizontal)].setMine(true);
        }
        //判断周围有几颗雷，记录下来
        for(int i=0;i<vertical;i++){
            for (int j=0;j<horizontal;j++){
//                squares[i][j] = new Square(i,j);
            }
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                for(int i=0;i<vertical;i++){
                    for (int j=0;j<horizontal;j++){
                        Square square = squares[i][j];
                        if (x>square.getX()&&x<square.getX()+100&&y>square.getY()&&y<square.getY()+100){
                            if (square.isMine()){
                                for(int m=0;m<vertical;m++) {
                                    for (int n = 0; n < horizontal; n++) {
                                        squares[m][n].setStart(true);
                                    }
                                }
                                over = true;
                            }else {
                                int count = 0;
                                if (j - 1 >= 0 && i - 1 >= 0) {//上左
                                    if (squares[i - 1][j - 1].isMine()) {
                                        count++;
                                    }
                                }
                                if (i - 1 >= 0) {//上中
                                    if (squares[i - 1][j].isMine()) {
                                        count++;
                                    }
                                }
                                if (j + 1 < horizontal && i - 1 >= 0) {//上右
                                    if (squares[i - 1][j + 1].isMine()) {
                                        count++;
                                    }
                                }
                                if (j - 1 >= 0) {//中左
                                    if (squares[i][j - 1].isMine()) {
                                        count++;
                                    }
                                }
                                if (j + 1 < horizontal) {//中右
                                    if (squares[i][j + 1].isMine()) {
                                        count++;
                                    }
                                }
                                if (i + 1 < vertical && j - 1 >= 0) {//左下
                                    if (squares[i + 1][j - 1].isMine()) {
                                        count++;
                                    }
                                }
                                if (i + 1 < vertical) {//左中
                                    if (squares[i + 1][j].isMine()) {
                                        count++;
                                    }
                                }
                                if (i + 1 < vertical && j + 1 < horizontal) {//右下
                                    if (squares[i + 1][j + 1].isMine()) {
                                        count++;
                                    }
                                }
                                square.setStart(true);
                                //判断前后左右有没有地雷
                                square.setData("" + count);
                                if (count > 0) {
                                    square.setImage(ImgUtil.getBad());
                                }
                            }
                        }
                    }
                }
//                super.mouseReleased(e);
            }
        });

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
    }

    @Override
    public void update(Graphics g) {
        if (screenImage==null){
            screenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gScreen = screenImage.getGraphics();
        Color c = gScreen.getColor();
        gScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        paintComponent(gScreen);
        gScreen.setColor(c);
        g.drawImage(screenImage,0,0,null);
    }

    @Override
    public void paintComponent(Graphics g) {
        int n=0;
        for(int i=0;i<vertical;i++){
            for (int j=0;j<horizontal;j++){
                squares[i][j].draw(g,this);
                if (!squares[i][j].isMine()&&!squares[i][j].start){
                    n++;
                }
            }
        }
        if (n==0){
            Image imgOver = ImgUtil.getWin();
            g.drawImage(imgOver,20,250,GAME_WIDTH-40,200,this);
        }
        if (over){
            Image imgOver = ImgUtil.getOver();
            g.drawImage(imgOver,20,20,GAME_WIDTH-40,GAME_HEIGHT-100,this);
        }
    }

    private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Clean Mine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100,10);
        frame.setSize(GAME_WIDTH,GAME_HEIGHT);
        frame.setResizable(false);

        //Create and set up the content pane.
        StartGame newContentPane = new StartGame();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
