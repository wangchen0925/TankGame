package com.wangchen.tankGme1;

import java.util.Vector;

public class MyTank extends Tank {
    private Shot shot = null;
    private Vector<Shot> shots = new Vector<>();
    private boolean isLive = true;
    public MyTank(int x, int y) {
        super(x, y);
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }

    public void shotEnemyTank() {
        // 限制面板只有10颗我方子弹
        if (shots.size() > 10) {
            return;
        }

        //创建 Shot 对象, 根据当前MyTank对象的位置和方向来创建Shot
        switch (getDirection()) {//得到MyTank对象方向
            case 0: //向上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1: //向右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2: //向下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3: //向左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }

        shots.add(shot);
        //启动Shot线程
        new Thread(shot).start();
    }
}
