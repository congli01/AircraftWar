package edu.hitsz;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 开始界面
 * @author LICONG
 */
public class StartInterface {
    private JPanel mainPanel;
    private JButton simpleButton;
    private JButton regularButton;
    private JButton difficultButton;
    private JComboBox select;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel video;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("StartInterface");
        frame.setSize(512, 768);
        frame.setResizable(false);
        frame.setBounds(((int) screenSize.getWidth() - 512) / 2, 0, 512, 768);
        frame.setContentPane(new StartInterface().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public StartInterface() {
        simpleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                Main.diff = "简单";
                Main.filePath = "simpleRanking.dat";
                Main.music = select.getSelectedIndex();
                synchronized (Main.MAIN_LOCK) {
                    Main.MAIN_LOCK.notify();
                }
            }
        });
        regularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                Main.diff = "普通";
                Main.filePath = "regularRanking.dat";
                Main.music = select.getSelectedIndex();
                synchronized (Main.MAIN_LOCK) {
                    Main.MAIN_LOCK.notify();
                }
            }
        });
        difficultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                Main.diff = "困难";
                Main.filePath = "difficultRanking.dat";
                Main.music = select.getSelectedIndex();
                synchronized (Main.MAIN_LOCK) {
                    Main.MAIN_LOCK.notify();
                }
            }
        });
    }
}
