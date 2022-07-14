package edu.hitsz.shootStrategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 抽象策略接口
 *
 * @author LICONG
 */
public interface ShootStrategy {

    /**
     *射击方法
     * @param aX    飞机x坐标
     * @param aY    飞机y坐标
     * @param aSpeedX 飞机横向速度
     * @param aSpeedY 飞机纵向速度
     * @param power    火力
     * @param shootNum  射击数目
     * @param direction 子弹射击方向 (向上发射：-1，向下发射：1)
     * @param type 子弹类型（0：英雄机子弹、1：敌机子弹）
     * @return
     */
    List<BaseBullet> shoot(int aX, int aY, int aSpeedX, int aSpeedY, int power, int shootNum, int direction, int type);

}
