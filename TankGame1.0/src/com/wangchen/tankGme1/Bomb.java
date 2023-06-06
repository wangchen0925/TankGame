package com.wangchen.tankGme1;

public class Bomb {
    private int x;
    private int y;
    private int life = 9;
    private boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean live) {
        isLive = live;
    }

    public void lifeDown() {
        if(life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
