package edu.hitsz.factory;

import edu.hitsz.prop.BaseProp;

/**
 * @author LICONG
 */
public interface PropFactory {

    /**
     * 创建道具的抽象方法
     * @param locationX
     * @param locationY
     * @param speedX
     * @param speedY
     * @return
     */
    BaseProp creatProp(int locationX, int locationY, int speedX, int speedY);
}
