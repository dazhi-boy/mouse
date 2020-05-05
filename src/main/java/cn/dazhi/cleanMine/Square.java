package cn.dazhi.cleanMine;

import com.sun.deploy.util.StringUtils;

import javax.swing.*;
import java.awt.*;

public class Square {
    private int x;
    private int y;
    private String data = "0";
    public boolean start = false;
    private int space = 100;
    private boolean mine;
    private Image image = ImgUtil.getGood();

    public Square(int x,int y){
        this.x = 200+x*space;
        this.y = 50+y*space;
    }

    public void draw(Graphics g, JPanel jPanel){
        if (start) {
            g.drawImage(image, x + 10, y + 10, space - 20, space - 20, jPanel);
        }
        g.setColor(Color.red);
        g.drawRect(x,y,space,space);
        if (!"0".equals(data)){
            g.setColor(Color.yellow);
            g.fillRect(x,y+70,100,30);
            g.setColor(Color.red);
            g.setFont(new Font("宋体",Font.BOLD,20));
            g.drawString(data,x+40,y+90);
        }
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
        if (mine){
            image = ImgUtil.getMine();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMine() {
        return mine;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
