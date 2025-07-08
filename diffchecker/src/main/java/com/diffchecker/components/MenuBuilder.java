package com.diffchecker.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBuilder {
  public static JMenuBar buildMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem exitItem = new JMenuItem("Exit");

    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.addSeparator();
    fileMenu.add(exitItem);
    fileMenu.setBorder(BorderFactory.createEmptyBorder());
    fileMenu.setForeground(new Color(157, 157, 157));
    menuBar.setBackground(new Color(36, 37, 38));
    menuBar.add(fileMenu);
    return menuBar;
  }
}
