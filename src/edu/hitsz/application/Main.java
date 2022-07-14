package edu.hitsz.application;

import edu.hitsz.SimpleTable;
import edu.hitsz.StartInterface;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.game.DifficultGame;
import edu.hitsz.game.EasyGame;
import edu.hitsz.game.Game;
import edu.hitsz.game.RegularGame;
import edu.hitsz.music.MusicThread;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static final Object MAIN_LOCK = new Object();
    /**
     * 游戏难度
     */
    public static String diff;

    public static Game game;

    /**
     * 音效开关
     * music=0 音效开启
     * music=1 音效关闭
     */
    public static int music;

    /**
     * 玩家信息存储文件路径
     */
    public static String filePath;

    public static void main(String[] args) throws IOException {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //第一个界面
        StartInterface startInterface = new StartInterface();
        JPanel startMainPanel = startInterface.getMainPanel();
        frame.setContentPane(startMainPanel);
        frame.setVisible(true);

        synchronized (MAIN_LOCK) {
            while (startMainPanel.isVisible()) {
                //主线程等待开始面板关闭
                try {
                    MAIN_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //移除第一个panel
        frame.remove(startMainPanel);

        if(diff == "简单"){
            game = new EasyGame();
        } else if (diff == "普通") {
            game = new RegularGame();
        } else {
            game = new DifficultGame();
        }

//        frame.add(game);
        frame.setContentPane(game);
        frame.setVisible(true);
        game.action();

        //背景音乐
        if(music == 0) {
            MusicThread bgm = new MusicThread("src\\videos\\bgm.wav");
            bgm.start();

            while(HeroAircraft.getHeroAircraft().getHp() > 0) {
                bgm.running = true;
                //背景音乐循环播放
                if (bgm.ending) {
                    bgm = new MusicThread("src\\videos\\bgm.wav");
                    bgm.start();
                }
            }
            //英雄机摧毁，背景音乐停止
            bgm.running = false;
        }

        synchronized (MAIN_LOCK) {
            while (game.isVisible()) {
                //主线程等待开始面板关闭
                try {
                    MAIN_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        SimpleTable simpleTable = new SimpleTable();
        simpleTable.action();
        JPanel tablePanel = simpleTable.getMainPanel();
        frame.setContentPane(tablePanel);
        frame.setVisible(true);
    }
}
