package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;

/**
 * @author LICONG
 */
public class BombPropFactory implements PropFactory{
    @Override
    public BaseProp creatProp(int locationX, int locationY, int speedX, int speedY) {
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
