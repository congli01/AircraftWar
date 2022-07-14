package edu.hitsz.game;

import edu.hitsz.aircraft.BaseEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.music.MusicThread;

import java.util.Random;

/**
 * 困难模式
 * 难度递增
 * 普通敌机血量90、精英敌机血量120
 * 敌机纵向速度10
 * 精英敌机概率1/6~1/2
 * 敌机产生和射击周期450ms~270ms
 * 最大敌机数6~14
 * Boss机阈值200，血量递增
 * @author LICONG
 */
public class DifficultGame extends Game{

    private int addDifficultyFlag;

    /**
     * Boss机在游戏中出现的次数
     * 每次召唤的Boss机的血量提升
     */
    private int bossCreateTime;

    public DifficultGame() {
        super();
        this.image = ImageManager.BACKGROUND_IMAGE5;
        rate = 6;
        cycleDuration = 450;
        enemyMaxNumber = 6;
        threshhold = 200;
        System.out.println("普通模式,初始难度：");
        System.out.println("普通敌机血量90、精英敌机血量120");
        System.out.println("敌机初始纵向速度10");
        System.out.println("精英机概率" + String.format("%.2f", (double) 1/rate) );
        System.out.println("敌机产生和射击周期" + cycleDuration + "ms");
        System.out.println("最大敌机数" + enemyMaxNumber);
        System.out.println("分数达到200出现boss机，血量递增");
        System.out.println("游戏难度递增");
        addDifficultyFlag = 0;
        bossCreateTime = 0;
    }

    @Override
    public void generateEnemies() {
        if (timeCountAndNewCycleJudge()) {
            addDifficultyFlag++;
            // 新敌机产生
            if (baseEnemies.size() < enemyMaxNumber) {

                Random random = new Random();
                int rd = random.nextInt(rate);
                if(rd == 1) {
                    //产生精英机
                    BaseEnemy baseEnemy = eliteFactory.creatEnemy();
                    baseEnemy.setSpeedY(10);
                    baseEnemy.decreaseHp(-60);
                    baseEnemies.add(baseEnemy);
                    bombReactors.add(baseEnemy);
                } else {
                    //产生普通敌机
                    BaseEnemy baseEnemy = commonFactory.creatEnemy();
                    baseEnemy.setSpeedY(10);
                    baseEnemy.decreaseHp(-60);
                    baseEnemies.add(baseEnemy);
                    bombReactors.add(baseEnemy);
                }

                //分数达到阈值，产生boss机
                if(dscore >= threshhold && (boss == null || boss.notValid())) {
                    boss = bossFactory.creatEnemy();
                    //每次召唤的boss机的血量比上次高30
                    boss.decreaseHp(-bossCreateTime * 30);
                    baseEnemies.add(boss);
                    bombReactors.add(boss);
                    bossCreateTime++;

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

            //增加游戏难度
            addDifficulty();
        }
    }

    private void addDifficulty(){
        if (addDifficultyFlag % 50 == 0 && addDifficultyFlag %100 != 0 && enemyMaxNumber <= 14){
            enemyMaxNumber++;
            System.out.println("提高难度！精英机概率" + String.format("%.2f", (double) 1/rate) + "，敌机周期" + cycleDuration +
                    "ms，最大敌机数" + enemyMaxNumber);
        } else if(addDifficultyFlag % 100 == 0 && addDifficultyFlag % 150 != 0 && rate > 2) {
            rate--;
            System.out.println("提高难度！精英机概率" + String.format("%.2f", (double) 1/rate) + "，敌机周期" + cycleDuration +
                    "ms，最大敌机数" + enemyMaxNumber);
        } else if (addDifficultyFlag % 150 == 0 && cycleDuration > 270) {
            cycleDuration = cycleDuration - 30;
            System.out.println("提高难度！精英机概率" + String.format("%.2f", (double) 1/rate) + "，敌机周期" + cycleDuration +
                    "ms，最大敌机数" + enemyMaxNumber);
        }
    }
}
