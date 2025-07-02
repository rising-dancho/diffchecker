package com.diffchecker.java_fundamentals.swing_and_awt;

// import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JButton_ extends JFrame {
  public static void main(String[] args) {
    new JButton_();
  }

  public JButton_() {
    setTitle("First Swing GUI");
    setSize(1080, 720);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    // setLayout(new BorderLayout());
    setLayout(null);

    // TOP LEVEL CONTAINERS
    // INTERMEDIATE CONTAINERS
    // ATOMIC COMPONENTS

    JButton btn1 = new JButton();
    btn1.setText("Click Me");
    btn1.setSize(100, 30);
    btn1.setLocation(0, 0);

    add(btn1);

    // Add the toolbar to the top
    setVisible(true);
  }
}
