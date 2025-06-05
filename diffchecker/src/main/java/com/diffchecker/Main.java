package com.diffchecker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main {
    static JFrame frame = new JFrame();
    static ImageIcon image = new ImageIcon(Main.class.getResource("/diffchecker/images/logo.png"));

    public static void main(String[] args) {
        // Set up frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(0x1F1F1F));

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create "File" menu
        JMenu fileMenu = new JMenu("File");

        // Create menu items
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Add action to "Exit"
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add menu items to the File menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator(); // adds a separator line
        fileMenu.add(exitItem);

        // Add File menu to the menu bar
        menuBar.add(fileMenu);

        // Set the menu bar to the frame
        frame.setJMenuBar(menuBar);

        // Show the frame after setting up everything
        frame.setVisible(true);
    }
}
