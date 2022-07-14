package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombReactor;

import java.util.List;


/**
 * 敌机抽象类
 *
 * @author LICONG
 */
public abstract class BaseEnemy extends AbstractAircraft implements BombReactor {

    public BaseEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        return null;
    }

    /**
     * 产生道具的抽象方法
     * @return List<BaseProp></BaseProp>
     * */
    public abstract BaseProp createProp();

    @Override
    public void bombReact() {
        this.vanish();
    }

    @Override
    public boolean unSubed() {
        return this.notValid();
    }
}
