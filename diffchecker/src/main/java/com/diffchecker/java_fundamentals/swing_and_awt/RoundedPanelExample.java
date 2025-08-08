package com.diffchecker.java_fundamentals.swing_and_awt;

import javax.swing.*;
import java.awt.*;
import com.diffchecker.components.RoundedBorder;

public class RoundedPanelExample {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Rounded Border Panel");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 300);
      frame.setLayout(new FlowLayout());

      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(200, 100));
      panel.setBackground(Color.WHITE);

      // Apply your custom rounded border
      panel.setBorder(new RoundedBorder(20, Color.BLUE, 3));

      frame.add(panel);
      frame.setVisible(true);
    });
  }
}
