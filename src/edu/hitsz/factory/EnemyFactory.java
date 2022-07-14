package edu.hitsz.factory;


import edu.hitsz.aircraft.BaseEnemy;

/**
 * 敌机工厂接口
 *
 * @author LICONG
 */
public interface EnemyFactory {
    /**
     * 创建敌机的抽象方法
     * @return
     */
    BaseEnemy creatEnemy();
}
