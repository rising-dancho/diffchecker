package com.diffchecker.components;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class RoundedTabbedPaneUI extends BasicTabbedPaneUI {

  private final Color selectedColor = new Color(0x363839);
  private final Color unselectedColor = new Color(0x242526);
  private final int arc = 6; // Increase for more roundness

  // This controls space around the tabs:
  @Override
  protected void installDefaults() {
    super.installDefaults();
    tabInsets = new Insets(6, 6, 6, 6); // top, left, bottom, right (more padding)
    tabAreaInsets = new Insets(5, 10, 5, 0); // space around tab area
    contentBorderInsets = new Insets(0, 0, 0, 0);
  }

  @Override
  protected void paintTabBackground(Graphics g, int tabPlacement,
      int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(isSelected ? selectedColor : unselectedColor);
    g2.fillRoundRect(x + 2, y + 5, w - 4, h - 10, arc, arc);
  }

  @Override
  protected void paintContentBorder(Graphics g, int tabPlacement,
      int selectedIndex) {
    // Optional: don't paint a border
  }

  @Override
  protected void paintFocusIndicator(Graphics g, int tabPlacement,
      Rectangle[] rects, int tabIndex,
      Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    // Do not paint focus indicator
  }

 @Override
protected void paintTabBorder(Graphics g, int tabPlacement,
        int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    if (!isSelected) return;

    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(new Color(0x5A5A5A)); // border color
    int arc = 6;
    g2.setStroke(new BasicStroke(1.5f));
    g2.drawRoundRect(x + 2, y + 5, w - 4, h - 10, arc, arc);

    g2.dispose();
}

  @Override
  protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
      int tabIndex, String title, Rectangle textRect, boolean isSelected) {
    g.setFont(font);
    g.setColor(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
    g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
  }
}

