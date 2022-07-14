package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BloodProp;

/**
 * @author LICONG
 */
public class BloodPropFactory implements PropFactory{
    @Override
    public BaseProp creatProp(int loacationX, int locationY, int speedX, int speedY) {
        return new BloodProp(loacationX, locationY, speedX, speedY);
    }
}
