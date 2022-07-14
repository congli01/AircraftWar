package edu.hitsz.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 数据访问对象DAO接口
 * @author LICONG
 */
public interface PlayerDao {
    /**
     * 获取排行榜中全部玩家的信息
     * @return
     */
    List<Player> getAllPlayers (String path) throws IOException;

    /**
     * 向排行榜中增加玩家
     * @param player 需要增加的玩家对象
     */
    void addPlayer(String path, Player player);

    /**
     * 删除玩家
     * @param i 玩家序号
     */
    void deletePlayer(String path, int i) throws IOException;
}
