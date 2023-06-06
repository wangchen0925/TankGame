package com.wangchen.tankGme1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank = null;   // 定义我方坦克

    //定义敌人坦克，放入到Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 5;

    Vector<Bomb> bombs = new Vector<>();

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel() {
        //nodes = Recorder.getNodesAndEnemyTankRec();
        //将MyPanel对象的 enemyTanks 设置给 Recorder 的 enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        myTank = new MyTank(500,500);   // 初始化我方坦克


        //初始化敌人坦克
        for (int i = 0; i < enemyTankSize; i++) {
            //创建一个敌人的坦克
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            //将enemyTanks 设置给 enemyTank !!!
            enemyTank.setEnemyTanks(enemyTanks);
            //设置方向
            enemyTank.setDirection(2);
            //启动敌人坦克线程，让他动起来
            new Thread(enemyTank).start();
            //给该enemyTank 加入一颗子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
            //加入enemyTank的Vector 成员
            enemyTank.getShots().add(shot);
            //启动 shot 对象
            new Thread(shot).start();
            //加入
            enemyTanks.add(enemyTank);
        }   // 初始化敌方坦克

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

    }

    public void showInfo(Graphics g) {

        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("累计击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);//画出一个敌方坦克
        g.setColor(Color.BLACK);//这里需要重新设置成黑色
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 画出自己的坦克
        g.fillRect(0,0,1000,750);
        showInfo(g);
        if (myTank != null && myTank.getIsLive()) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
        }

        //画出自己的坦克射击的子弹
//        if(myTank.shot != null && myTank.shot.getIsLive() == true) {
//            g.draw3DRect(myTank.shot.getX(), myTank.shot.getY(), 1, 1, false);
//        }
        for (int i = 0; i < myTank.getShots().size(); i++) {
            Shot shot = myTank.getShots().get(i);
            if(shot != null && shot.getIsLive() == true) {
                g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
            } else {
                myTank.getShots().remove(shot);
            }
        }

        //如果bombs 集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.getLife() > 6) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 3) {
                g.drawImage(image2, bomb.getX(), bomb.getY(), 60, 60, this);
            } else {
                g.drawImage(image3, bomb.getX(), bomb.getY(), 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life 为0, 就从bombs 的集合中删除
            if (bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }

        for (int i = 0; i < enemyTanks.size(); i++) {
            //从Vector 取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否还存活
            if (enemyTank.getIsLive()) {//当敌人坦克是存活的，才画出该坦克
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                //画出 enemyTank 所有子弹
                for (int j = 0; j < enemyTank.getShots().size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.getShots().get(j);
                    //绘制
                    if (shot.getIsLive()) { //isLive == true
                        g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                    } else {
                        //从Vector 移除
                        enemyTank.getShots().remove(shot);
                    }
                }
            }
        }
    }

    /**
     * @param x   坦克横坐标
     * @param y   坦克纵坐标
     * @param g   画笔
     * @param direction   方向
     * @param type   类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {
            case 0:   // 我方坦克
                g.setColor(Color.cyan);
                break;
            case 1:   // 敌方坦克
                g.setColor(Color.yellow);
                break;
        }

        switch (direction) {
            case 0:   // 向上
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y, x + 20, y + 30);
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮筒
                break;
        }
    }

    public void hitMyTank() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.getShots().size(); j++) {
                Shot shot = enemyTank.getShots().get(j);
                if (myTank.getIsLive() && shot.getIsLive()) {
                    hitTank(shot, myTank);
                }
            }
        }
    }

    public void hitEnemyTank() {
        for (int i = 0; i < myTank.getShots().size(); i++) {
            Shot shot = myTank.getShots().get(i);
            if (shot != null && shot.getIsLive()) {//当我的子弹还存活
                //遍历敌人所有的坦克
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirection()) {
            case 0:   // 向上
            case 2:   // 向下
                if (s.getX() > tank.getX()
                        && s.getX() < tank.getX() + 40
                        && s.getY() > tank.getY()
                        && s.getY() < tank.getY() + 60) {
                    s.setIsLive(false);
                    tank.setIsLive(false);
                    //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
                    enemyTanks.remove(tank);

                    //当我方击毁一个敌人坦克时，就对数据allEnemyTankNum++
                    //解读, 因为 enemyTank 可以是 Hero 也可以是 EnemyTank
                    if (tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:   // 向右
            case 3:   // 向左
                if (s.getX() > tank.getX()
                        && s.getX() < tank.getX() + 60
                        && s.getY() > tank.getY()
                        && s.getY() < tank.getY() + 40) {
                    s.setIsLive(false);
                    tank.setIsLive(false);
                    //当我的子弹击中敌人坦克后，将enemyTank 从Vector 拿掉
                    enemyTanks.remove(tank);

                    //当我方击毁一个敌人坦克时，就对数据allEnemyTankNum++
                    //解读, 因为 enemyTank 可以是 Hero 也可以是 EnemyTank
                    if (tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //改变坦克的方向
            myTank.setDirection(0);
            if (myTank.getY() > 0) {
                myTank.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//D键, 向右
            myTank.setDirection(1);
            if (myTank.getX() + 60 < 1000) {
                myTank.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//S键
            myTank.setDirection(2);
            if (myTank.getY() + 60 < 750) {
                myTank.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//A键
            myTank.setDirection(3);
            if (myTank.getX() > 0) {
                myTank.moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {// 空格键
//            if (myTank.shot == null || !myTank.shot.getIsLive()) {
//                myTank.shotEnemyTank();
//            }
            myTank.shotEnemyTank();
        }

        //让面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        //每隔 100毫秒，重绘区域, 刷新绘图区域, 子弹就移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitMyTank();
            hitEnemyTank();
            this.repaint();
        }
    }
}
