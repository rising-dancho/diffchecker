package com.diffchecker.java_fundamentals.swing_and_awt.pos_system;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;

public class MainScreen extends JFrame {

  public MainScreen() throws HeadlessException {
    createNavButtonsPanel();

  }

  public void createNavButtonsPanel() {
    JPanel p1 = new JPanel();
    TitledBorder titledBorder = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.GRAY, 1), "Navigation Buttons", TitledBorder.CENTER,
        TitledBorder.DEFAULT_POSITION);
    p1.setBorder(titledBorder);
    p1.setBounds(15, 50, 150, 250);
    p1.setLayout(new GridLayout(5, 1));

    // for the Jframe
    setLayout(null);
    add(p1);

  }

  public static void main(String[] args) {
    MainScreen mainScreen = new MainScreen();
    mainScreen.setVisible(true);
    mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainScreen.setTitle("POS SYSTEM");
    mainScreen.setBounds(0, 0, 1200, 800);
  }
}
