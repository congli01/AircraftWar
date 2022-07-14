package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.factory.BulletPropFactory;
import edu.hitsz.factory.PropFactory;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.shootStrategy.ShootStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Boss机
 * 可射击
 * @author LICONG
 */
public class Boss extends BaseEnemy {

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 10;

    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */
    private int direction = 1;

    public Boss(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootStrategy = shootStrategy;
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this.getLocationX(), this.getLocationY(), speedX, speedY, power, shootNum, direction, 1);
    }

    @Override
    public BaseProp createProp() {
        int x = this.getLocationX();
        int y = this.getLocationY();
        int speedX = 0;
        int speedY = 5;
        int num1 = 0;
        int num2 = 1;
        int num3 = 2;
        int num4 = 3;
        int num5 = 4;
        PropFactory propFactory;
        BaseProp baseProp;
        Random random = new Random();
        int rd = random.nextInt(10);
        if (rd == num1 || rd == num2) {
            propFactory = new BloodPropFactory();
            baseProp = propFactory.creatProp(x, y, speedX, speedY);
            return baseProp;
        } else if (rd == num3) {
            propFactory = new BombPropFactory();
            baseProp = propFactory.creatProp(x, y, speedX, speedY);
            return baseProp;
        } else if (rd == num4 || rd == num5) {
            propFactory = new BulletPropFactory();
            baseProp = propFactory.creatProp(x, y, speedX, speedY);
            return baseProp;
        } else {
            return null;
        }
    }

    @Override
    public void bombReact() {
        hp = hp - 30;
    }
}
