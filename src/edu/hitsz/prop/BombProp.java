package edu.hitsz.prop;


import edu.hitsz.aircraft.BaseEnemy;
import edu.hitsz.aircraft.Boss;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.PropFunc;

import java.util.LinkedList;
import java.util.List;

/**
 * 炸弹道具类
 * @author LICONG
 */
public class BombProp extends BaseProp implements PropFunc {
    private List<BombReactor> bombReactors;

    public BombProp (int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void addReactor(List<BombReactor> reactors) {
        this.bombReactors = reactors;
    }

    public void removeReactor() {
//        System.out.println(bombReactors.size());
        for (int i = 0; i < bombReactors.size(); i++){
            if (bombReactors.get(i).unSubed()) {
                bombReactors.remove(i);
            }
        }
//        System.out.println(bombReactors.size());
    }

    @Override
    public void func() {
        for (BombReactor bombReactor : bombReactors) {
            bombReactor.bombReact();
        }
        removeReactor();
        System.out.println("BombSupply active!");
    }
}
