package com.diffchecker.components;

import java.awt.*;
import javax.swing.*;

public class RoundedPainter {
  public static JComponent createRoundedWrapper(JComponent inner, int radius, int stroke, Color borderColor, Color bgColor) {
    JPanel panel = new JPanel(null) {
      @Override
      protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background with rounded corners
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Draw border
        if (stroke > 0) {
          g2.setStroke(new BasicStroke(stroke));
          g2.setColor(borderColor);
          g2.drawRoundRect(stroke / 2, stroke / 2, getWidth() - stroke, getHeight() - stroke, radius, radius);
        }

        g2.dispose();
      }

      @Override
      protected void paintBorder(Graphics g) {
        // no default border
      }
    };

    panel.setOpaque(false);
    inner.setOpaque(false); // optional, avoids red box showing behind
    panel.add(inner);
    return panel;
  }
}
