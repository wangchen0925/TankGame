# 坦克大战1.0(TankGame)

## 项目介绍
该项目是本人学习Java的过程中一个实践项目，运用到Java集合、泛型编程、多线程、IO流等知识，目前1.0版本实现了坦克移动、射击子弹、防止敌人坦克重叠、记录击毁敌人坦克数目等功能。
<br />
参考资料 【韩顺平讲Java】Java坦克项目 面向对象 多线程 集合 IO流 算法 https://www.bilibili.com/video/BV1Ay4y1t7hz/?p=53&share_source=copy_web&vd_source=6cc6a719eb1f28b8549acebdb3680a79
## 各文件说明
Bomb.java   爆炸效果类<br />
EnemyTank.java   敌方坦克类<br />
MyJrame.java   主窗口，运行其中的main方法即可运行程序<br />
MyPanel.java   绘制游戏区域<br />
MyTank.java   我方坦克类<br />
Node.java   记录退出游戏时剩余敌方坦克的坐标方向<br />
Recorder.java   记录退出游戏时的得分以及其他信息，并写入到myRecord.txt文件<br />
Shot.java   射击类<br />
Tank.java   坦克类，是MyTank.java和EnemyTank.java的父类<br />

## 效果
![image](https://github.com/wangchen0925/TankGame/assets/124268522/35d36e81-ae2c-4148-bf90-dfed8869a7a8)
![image](https://github.com/wangchen0925/TankGame/assets/124268522/bb7ef26e-67d5-42cd-aec8-c31b7cacb113)
![image](https://github.com/wangchen0925/TankGame/assets/124268522/b1e693d3-d13c-4674-b591-3f8d9c9c8bdc)
