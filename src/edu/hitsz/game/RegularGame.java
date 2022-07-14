package edu.hitsz.game;

import edu.hitsz.aircraft.BaseEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.music.MusicThread;

import java.awt.*;
import java.util.Random;

/**
 * 普通模式
 * 难度递增
 * 普通敌机血量60、精英敌机血量90
 * 敌机纵向速度8
 * 精英敌机概率1/8~1/3
 * 敌机产生和射击周期550ms
 * 最大敌机数5~10
 * Boss机阈值300，血量固定
 * @author LICONG
 */
public class RegularGame extends Game{

    private int addDifficultyFlag;

    public RegularGame () {
        super();
        this.image = ImageManager.BACKGROUND_IMAGE;
        rate = 8;
        cycleDuration = 550;
        threshhold = 300;
        System.out.println("普通模式,初始难度：");
        System.out.println("普通敌机血量60、精英敌机血量90");
        System.out.println("敌机初始纵向速度8");
        System.out.println("精英机概率" + String.format("%.2f", (double) 1/rate) );
        System.out.println("敌机产生和射击周期" + cycleDuration + "ms");
        System.out.println("最大敌机数" + enemyMaxNumber);
        System.out.println("分数达到300出现boss机，血量固定");
        System.out.println("游戏难度递增");
        addDifficultyFlag = 0;
    }

    @Override
    public void generateEnemies() {
        if (timeCountAndNewCycleJudge()) {
//            System.out.println(time);
            addDifficultyFlag++;
            // 新敌机产生
            if (baseEnemies.size() < enemyMaxNumber) {

                Random random = new Random();
                int rd = random.nextInt(rate);
                if(rd == 1) {
                    //产生精英机
                    BaseEnemy baseEnemy = eliteFactory.creatEnemy();
                    baseEnemy.setSpeedY(8);
                    baseEnemy.decreaseHp(-30);
                    baseEnemies.add(baseEnemy);
                    bombReactors.add(baseEnemy);
                } else {
                    //产生普通敌机
                    BaseEnemy baseEnemy = commonFactory.creatEnemy();
                    baseEnemy.setSpeedY(8);
                    baseEnemy.decreaseHp(-30);
                    baseEnemies.add(baseEnemy);
                    bombReactors.add(baseEnemy);
                }

                //分数达到阈值，产生boss机
                if(dscore >= threshhold && (boss == null || boss.notValid())) {
                    boss = bossFactory.creatEnemy();
                    baseEnemies.add(boss);
                    bombReactors.add(boss);

                    //boss登场音乐
                    if (Main.music == 0) {
                        bgmBoss = new MusicThread("src\\videos\\bgm_boss.wav");
                        bgmBoss.start();
                    }

                    dscore = 0;
                }
            }

            // 飞机射出子弹
            shootAction();

            //增加难度
            addDifficulty();
        }
    }

    private void addDifficulty() {
        if (addDifficultyFlag % 50 == 0 && addDifficultyFlag % 100 != 0 && enemyMaxNumber <= 10){
            enemyMaxNumber++;
            System.out.println("提高难度！精英机概率" + String.format("%.2f", (double) 1/rate) + "，敌机周期" + cycleDuration +
                    "ms，最大敌机数" + enemyMaxNumber);
        } else if(addDifficultyFlag % 100 == 0 && rate > 3) {
            rate--;
            System.out.println("提高难度！精英机概率" + String.format("%.2f", (double) 1/rate) + "，敌机周期" + cycleDuration +
                    "ms，最大敌机数" + enemyMaxNumber);
        }
    }
}
