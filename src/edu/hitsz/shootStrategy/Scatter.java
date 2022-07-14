package edu.hitsz.shootStrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 散射，实现了射击策略接口
 * @author LICONG
 */
public class Scatter implements ShootStrategy{

    @Override
    public List<BaseBullet> shoot(int aX, int aY, int aSpeedX, int aSpeedY, int power, int shootNum, int direction, int type) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aX;
        int y = aY + direction*2;
        int speedX = aSpeedX;
        int speedY = aSpeedY + direction*5;
        BaseBullet baseBullet1, baseBullet2, baseBullet3;
        for(int i=0; i<shootNum; i++){
            if (type == 0) {
                baseBullet1 = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
                baseBullet2 = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX+2, speedY, power);
                baseBullet3 = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX-2, speedY, power);
            } else {
                baseBullet1 = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
                baseBullet2 = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX+2, speedY, power);
                baseBullet3 = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX-2, speedY, power);
            }

            res.add(baseBullet1);
            res.add(baseBullet2);
            res.add(baseBullet3);
        }
        return res;
    }
}
