package edu.hitsz.dao;

import edu.hitsz.application.Main;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * DAO接口的实现类
 * @author LICONG
 */
public class PlayerDaoImpl implements PlayerDao{
    @Override
    public List<Player> getAllPlayers(String path) throws IOException {
        List<Player> players = new LinkedList<>();
        FileInputStream fileInputStream = new FileInputStream(new File(Main.filePath));
        ObjectInputStream reader = new ObjectInputStream(fileInputStream);
        while (true) {
            try {
                players = (List<Player>) reader.readObject();
            } catch (EOFException | ClassNotFoundException e) {
                break;
            }
        }
        fileInputStream.close();
        reader.close();
        return players;
    }

    @Override
    public void addPlayer(String path, Player player) {
        List<Player> players = new LinkedList<>();
        try {
            //排行榜是否已经存在
            File file = new File(Main.filePath);
            if(file.exists()){
                players = this.getAllPlayers(Main.filePath);
            }

            players.add(player);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Main.filePath));
            oos.writeObject(players);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePlayer(String path, int i) throws IOException {
        List<Player> players;
        players = this.getAllPlayers(Main.filePath);
        players.sort(Comparator.comparingInt(Player::getScore).reversed());
        players.remove(i);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Main.filePath));
        oos.writeObject(players);
        oos.close();
    }


}
