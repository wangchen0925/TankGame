package com.wangchen.tankGme1;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class MyJFrame extends JFrame {
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        MyJFrame myJFrame = new MyJFrame();
    }

    public MyJFrame() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);   //将mp 放入到Thread ,并启动
        thread.start();
        this.add(mp);
        this.setSize(1300,950);
        this.addKeyListener(mp);//让JFrame 监听mp的键盘事件
        this.setDefaultCloseOperation(3);
        this.setVisible(true);

        //在JFrame 中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
