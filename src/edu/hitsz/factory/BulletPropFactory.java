package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BulletProp;

/**
 * @author LICONG
 */
public class BulletPropFactory implements PropFactory{
    @Override
    public BaseProp creatProp(int locationX, int locationY, int speedX, int speedY) {
        return new BulletProp(locationX, locationY, speedX, speedY);
    }
}
