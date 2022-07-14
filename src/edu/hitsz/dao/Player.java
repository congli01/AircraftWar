package edu.hitsz.dao;

import java.io.Serializable;

/**
 * 玩家类
 * 记录玩家姓名、最终得分
 * @author LICONG
 */
public class Player implements Serializable {
    private String name;
    private int score;
    private String date;

    public Player(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString(){
        return "Player{" + "name='" + name + '\'' +   ", age=" + score + ", date=" + date + '}';
    }
}
