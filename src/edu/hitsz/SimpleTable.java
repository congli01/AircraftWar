package edu.hitsz;

import edu.hitsz.application.Main;
import edu.hitsz.dao.Player;
import edu.hitsz.dao.PlayerDao;
import edu.hitsz.dao.PlayerDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 排行榜展示界面
 * @author LICONG
 */
public class SimpleTable {
    private JButton deleteButton;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel headerLabel;
    private JPanel mainPanel;
    private JTable scoreTable;
    private JScrollPane tableScrollPanel;
    private JLabel difficulty;
    private DefaultTableModel model;
    private PlayerDao playerDao = new PlayerDaoImpl();
    private List<Player> players;

    public SimpleTable() throws IOException {
        difficulty.setText("难度：" + Main.diff);

        //删除历史玩家得分
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(mainPanel, "是否确定删除选中的玩家", "选则一个选项", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == 0) {
                    int row = scoreTable.getSelectedRow();
//                    System.out.println(row);
                    if(row != -1) {
                        model.removeRow(row);
                        System.out.println("删除成功！");
                        try {
                            playerDao.deletePlayer(Main.filePath, row);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void action() throws IOException {
        add();
        display();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void display() throws IOException {
        players = playerDao.getAllPlayers(Main.filePath);
        players.sort(Comparator.comparingInt(Player::getScore).reversed());
        String[][] tableData = new String[players.size()][4];
        for(int i = 0; i < players.size(); i++) {
            tableData[i] = new String[]{Integer.toString(i+1), players.get(i).getName(), Integer.toString(players.get(i).getScore()), players.get(i).getDate()};
        }

        String[] columnName = {"名次", "玩家名", "得分", "记录时间"};

        model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
    }

    private void add() {
        //记录玩家该局得分
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        //显示对话框让用户输入名字
        String name = JOptionPane.showInputDialog(mainPanel, "游戏结束，你的得分为"+Main.game.getScore()+",请输入名字记录得分：", "输入", JOptionPane.QUESTION_MESSAGE);
        playerDao.addPlayer(Main.filePath, new Player(name, Main.game.getScore(), simpleDateFormat.format(date).toString()));
    }

//    public static void main(String[] args) throws IOException {
//        JFrame frame = new JFrame("SimpleTable");
//        frame.setContentPane(new SimpleTable().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }
}
