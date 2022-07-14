package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.factory.PropFunc;
import edu.hitsz.shootStrategy.Scatter;

/**
 * 火力道具类
 * @author LICONG
 */
public class BulletProp extends BaseProp implements PropFunc {

    public BulletProp (int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func() {
        HeroAircraft.getHeroAircraft().setShootStrategy(new Scatter());
        System.out.println("FireSupply active!");
    }
}
