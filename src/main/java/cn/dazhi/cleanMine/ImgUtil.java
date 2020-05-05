package cn.dazhi.cleanMine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ImgUtil {
    private static Random random = new Random();
    private static ArrayList<Image> goods = new ArrayList<>();
    private static ArrayList<Image> bads = new ArrayList<>();
    private static Image mine;

    static {
        goods.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/n12.png")));
        goods.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/n22.png")));
        goods.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/n32.png")));
        goods.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/n42.png")));

        bads.add(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/A10.png")));

        mine = Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/bomb.jpg"));
    }

    public static Image getGood(){
        return goods.get(random.nextInt(goods.size()));
    }

    public static Image getBad(){
        return bads.get(random.nextInt(bads.size()));
    }

    public static Image getMine(){
        return mine;
    }

    public static Image getOver() {
        return Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/over.png"));
    }

    public static Image getWin() {
        return Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/win.png"));
    }

    public static void main(String[] args) {
        Image img=Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("cleanMine/01.png"));
        System.out.println(img);
    }
}
