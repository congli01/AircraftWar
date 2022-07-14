package edu.hitsz.factory;


import edu.hitsz.aircraft.Boss;
import edu.hitsz.aircraft.BaseEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.shootStrategy.Scatter;

/**
 * Boss机工厂
 *
 * @author LICONG
 */
public class BossFactory implements EnemyFactory {
    @Override
    public BaseEnemy creatEnemy() {
        return new Boss((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                5,
                0,
                450,
                new Scatter());
    }
}
