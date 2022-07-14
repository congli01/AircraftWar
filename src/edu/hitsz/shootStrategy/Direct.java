package edu.hitsz.shootStrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 直射，实现了射击策略接口
 * @author LICONG
 */
public class Direct implements ShootStrategy{

    @Override
    public List<BaseBullet> shoot(int aX, int aY, int aSpeedX, int aSpeedY, int power, int shootNum, int direction, int type) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aX;
        int y = aY + direction*2;
        int speedX = 0;
        int speedY = aSpeedY + direction*5;
        BaseBullet baseBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (type == 0) {
                baseBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            } else {
                baseBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            }

            res.add(baseBullet);
        }
        return res;
    }
}
