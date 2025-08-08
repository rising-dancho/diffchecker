package com.diffchecker.java_fundamentals.swing_and_awt;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.diffchecker.components.RoundedPainter;

public class JPanel_ extends JFrame {
  public static void main(String[] args) {
    new JPanel_();
  }

  public JPanel_() {
    setTitle("Rounded Panel Example");
    setLayout(null);
    setSize(1080, 720);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(null);
    contentPanel.setBounds(0, 0, 300, 300); // relative to wrapper
    // Don't set background, make it transparent
    contentPanel.setOpaque(false);

    JLabel label = new JLabel("Hello Rounded!");
    label.setBounds(20, 20, 200, 30);
    contentPanel.add(label);

    JComponent roundedWrapper = RoundedPainter.createRoundedWrapper(
        contentPanel, 30, 3, Color.BLUE, Color.WHITE);

    roundedWrapper.setBounds(100, 100, 300, 300);
    add(roundedWrapper);

    setVisible(true);
  }
}
