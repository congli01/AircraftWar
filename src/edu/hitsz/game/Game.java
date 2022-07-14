package edu.hitsz.game;

import edu.hitsz.aircraft.*;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dao.Player;
import edu.hitsz.dao.PlayerDao;
import edu.hitsz.dao.PlayerDaoImpl;
import edu.hitsz.factory.BossFactory;
import edu.hitsz.factory.CommonFactory;
import edu.hitsz.factory.EliteFactory;
import edu.hitsz.music.MusicThread;
import edu.hitsz.prop.*;
import edu.hitsz.shootStrategy.Direct;
import edu.hitsz.shootStrategy.Scatter;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    protected int backGroundTop = 0;
    protected Image image;

    /**
     * 英雄机产生概率
     */
    protected int rate;

    /**
     * Scheduled 线程池，用于任务调度
     */
    protected final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    protected final HeroAircraft heroAircraft;
    protected final List<BaseEnemy> baseEnemies;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<BaseProp> baseProps;
    /**
     炸弹道具的订阅者
     */
    protected final List<BombReactor> bombReactors;


    protected int enemyMaxNumber = 5;

    protected boolean gameOverFlag = false;
    protected int score = 0;
    protected int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int cycleTime = 0;

    CommonFactory commonFactory = new CommonFactory();
    EliteFactory eliteFactory = new EliteFactory();
    BossFactory bossFactory = new BossFactory();

    /**
     * 阈值
     */
    protected int threshhold = 200;

    /**
     * 达到阈值后分数的变化
     */
    protected int dscore = 0;

    BaseEnemy boss;

    MusicThread bgmBoss = new MusicThread("src\\videos\\bgm_boss.wav");


    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        baseEnemies = new LinkedList<>();

        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        baseProps = new LinkedList<>();

        bombReactors = new LinkedList<>();


        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            generateEnemies();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            //Boss机音效控制
            if (Main.music == 0) {
                bgmBossControllor();
            }

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            checkGameStatus();

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    protected boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 抽象方法，产生敌机
     */
    protected abstract void generateEnemies();

    protected void shootAction() {
        // TODO 敌机射击
        for (BaseEnemy baseEnemy : baseEnemies) {
            if(baseEnemy instanceof EliteEnemy) {
                EliteEnemy eliteEnemy = (EliteEnemy) baseEnemy;
                List<BaseBullet> bullets = eliteEnemy.shoot();
                enemyBullets.addAll(bullets);
                bombReactors.addAll(bullets);
            } else if(baseEnemy instanceof Boss) {
                Boss boss = (Boss) baseEnemy;
                List<BaseBullet> bullets = boss.shoot();
                enemyBullets.addAll(bullets);
                bombReactors.addAll(bullets);
            }

        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    protected void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    protected void aircraftsMoveAction() {
        for (BaseEnemy baseEnemy : baseEnemies) {
            baseEnemy.forward();
        }
    }

    /**道具移动*/
    protected void propsMoveAction() {
        for (BaseProp baseProp : baseProps) {
            baseProp.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    protected void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            //英雄机撞击到敌机子弹  损失一定生命值
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (BaseEnemy baseEnemy : baseEnemies) {
                if (baseEnemy.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (baseEnemy.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    baseEnemy.decreaseHp(bullet.getPower());

                    //击中音效
                    if(Main.music == 0) {
                        new MusicThread("src\\videos\\bullet_hit.wav").start();
                    }

                    bullet.vanish();
                    if (baseEnemy.notValid()) {
                        // TODO 获得分数，产生道具补给
                        score += 10;
                        dscore += 10;
                        // 摧毁英雄机
                        // 额外获得20分
                        // 随机产生某种道具
                        if (baseEnemy instanceof EliteEnemy) {
                            score += 20;
                            dscore += 20;
                            BaseProp baseProp = ((EliteEnemy) baseEnemy).createProp();
                            if(baseProp != null) {
                                baseProps.add(baseProp);
                                if(baseProp instanceof BombProp) {
                                    ((BombProp) baseProp).addReactor(bombReactors);
                                }
                            }

                        } else if (baseEnemy instanceof Boss) {
                            score += 100;
                            BaseProp baseProp = ((Boss) baseEnemy).createProp();
                            if(baseProp != null) {
                                baseProps.add(baseProp);
                                if(baseProp instanceof BombProp) {
                                    ((BombProp) baseProp).addReactor(bombReactors);
                                }
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (baseEnemy.crash(heroAircraft) || heroAircraft.crash(baseEnemy)) {
                    baseEnemy.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (BaseProp baseProp : baseProps) {
            if (baseProp.notValid()) {
                continue;
            }
            if (heroAircraft.crash(baseProp)) {
                if (Main.music == 0) {
                    new MusicThread("src\\videos\\get_supply.wav").start();
                }
                if (baseProp instanceof BloodProp) {
                    ((BloodProp) baseProp).func();
                } else if (baseProp instanceof BombProp) {
                    ((BombProp) baseProp).func();
                    if(Main.music == 0) {
                        new MusicThread("src\\videos\\bomb_explosion.wav").start();
                    }
                } else if (baseProp instanceof BulletProp) {
                    //火力道具生效一段时间
                    Thread t = new Thread(() -> {
                        ((BulletProp) baseProp).func();
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        HeroAircraft.getHeroAircraft().setShootStrategy(new Direct());
                    });
                    t.start();

                }
                baseProp.vanish();
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    protected void postProcessAction() {
        for (BaseEnemy baseEnemy : baseEnemies) {
            //当上一架Boss机被击毁后重新计算得分的变化，避免立刻出现另一架Boss机
            if(baseEnemy instanceof Boss && baseEnemy.notValid()) {
                dscore = 0;
            }
        }
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        baseEnemies.removeIf(AbstractFlyingObject::notValid);
        baseProps.removeIf(AbstractFlyingObject::notValid);

    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(image, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(image, 0, this.backGroundTop, null);

        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, baseEnemies);
        paintImageWithPositionRevised(g, baseProps);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    protected void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    protected void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    protected void checkGameStatus() {
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            executorService.shutdown();
            gameOverFlag = true;
            System.out.println("Game Over!");

            //游戏结束音效
            if (Main.music == 0) {
                bgmBoss.running = false;
                new MusicThread("src\\videos\\game_over.wav").start();
            }

            this.setVisible(false);
            synchronized (Main.MAIN_LOCK){
                Main.MAIN_LOCK.notify();
            }
        }
    }

    public int getScore() {
        return score;
    }

    //Boss机音效控制
    protected void bgmBossControllor() {
        //boss机存活
        if(boss != null && !boss.notValid()) {
            if (heroAircraft.getHp() > 0) {
                bgmBoss.running = true;
                if (bgmBoss.ending) {
                    bgmBoss = new MusicThread("src\\videos\\bgm_boss.wav");
                    bgmBoss.start();
                }
            } else {
                //英雄机摧毁，Boss机音乐停止
                bgmBoss.running = false;
            }
        } else {
            //boss机摧毁，音乐停止
            bgmBoss.running = false;
        }
    }

}
