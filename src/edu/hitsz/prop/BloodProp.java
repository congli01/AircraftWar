package edu.hitsz.prop;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.factory.PropFunc;

/**
 * 加血道具类
 * @author LICONG
 */
public class BloodProp extends BaseProp implements PropFunc {

    HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();

    public BloodProp (int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func() {
        if (heroAircraft.getHp() < heroAircraft.getMaxHp()) {
            heroAircraft.decreaseHp(-10);
            System.out.println("HpSupply active!");
        } else {
            System.out.println("Your health point is full!");
        }
    }
}
