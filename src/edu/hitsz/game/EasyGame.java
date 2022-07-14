package edu.hitsz.game;

import edu.hitsz.aircraft.BaseEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.music.MusicThread;

import java.util.Random;

/**
 * 简单模式
 * 普通敌机血量30、精英敌机血量60
 * 敌机纵向速度6
 * 精英敌机概率1/10
 * 敌机产生和射击周期600ms
 * 最大敌机数5
 * 无Boss机
 * @author LICONG
 */
public class EasyGame extends Game{

    public EasyGame() {
        super();
        this.image = ImageManager.BACKGROUND_IMAGE2;
        rate = 10;
        System.out.println("简单模式：\n普通敌机血量30、精英敌机血量60");
        System.out.println("敌机纵向速度6");
        System.out.println("精英敌机产生概率" + String.format("%.2f", (double) 1/rate));
        System.out.println("敌机产生和射击周期600ms");
        System.out.println("最大敌机数5，无Boss机");
        System.out.println("游戏难度固定");
    }

    @Override
    protected void generateEnemies() {
        if (timeCountAndNewCycleJudge()) {
            // 新敌机产生
            if (baseEnemies.size() < enemyMaxNumber) {

                Random random = new Random();
                int rd = random.nextInt(rate);
                if (rd == 1) {
                    //产生精英机
                    BaseEnemy baseEnemy = eliteFactory.creatEnemy();
                    baseEnemies.add(baseEnemy);
                    bombReactors.add(baseEnemy);
                } else {
                    //产生普通敌机
                    BaseEnemy baseEnemy = commonFactory.creatEnemy();
                    baseEnemies.add(baseEnemy);
                    bombReactors.add(baseEnemy);
                }

                // 飞机射出子弹
                shootAction();
            }
        }
    }
}
