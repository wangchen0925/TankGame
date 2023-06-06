package com.wangchen.tankGme1;

public class Tank {
    private int x;   // 横坐标
    private int y;   // 纵坐标
    private int speed = 2;   // 速度

    private int direction;

    private boolean isLive;

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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean live) {
        isLive = live;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        y -= 1;
    }

    public void moveRight() {
        x += 1;
    }

    public void moveDown() {
        y += 1;
    }

    public void moveLeft() {
        x -= 1;
    }
}
