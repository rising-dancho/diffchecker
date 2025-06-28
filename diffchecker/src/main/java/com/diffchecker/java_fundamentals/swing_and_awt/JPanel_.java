package com.diffchecker.java_fundamentals.swing_and_awt;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanel_ extends JFrame {
  public static void main(String[] args) {
    new JPanel_();
  }

  public JPanel_() {
    // JFRAME
    // top level container
    setTitle("First Swing GUI");
    setLayout(null);
    setSize(1080, 720);
    setLocationRelativeTo(null);
    setVisible(true);

    // JPANEL
    JPanel jpanel = new JPanel();
    jpanel.setLayout(null);
    jpanel.setSize(300, 230);
    jpanel.setBackground(Color.red);
    jpanel.setVisible(true);

    add(jpanel);

  }
}
