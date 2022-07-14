package edu.hitsz.factory;


import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.BaseEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootStrategy.Direct;

/**
 * 精英敌机工厂
 *
 * @author LICONG
 */
public class EliteFactory implements EnemyFactory {
    @Override
    public BaseEnemy creatEnemy() {
        return new EliteEnemy((int) (Math.random() *(Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                (int) (Math.random()*10 - 5),
                4,
                60,
                new Direct());
    }
}
