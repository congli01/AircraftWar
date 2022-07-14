package edu.hitsz.factory;


import edu.hitsz.aircraft.CommonEnemy;
import edu.hitsz.aircraft.BaseEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * 普通敌机工厂
 * 创建普通敌机并返回
 *
 * @author LICONG
 */
public class CommonFactory implements EnemyFactory {
    @Override
    public BaseEnemy creatEnemy() {
        return new CommonEnemy((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.COMMON_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                0,
                6,
                30);
    }
}
