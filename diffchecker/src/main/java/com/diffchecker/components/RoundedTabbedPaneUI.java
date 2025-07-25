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
    tabAreaInsets = new Insets(5, 5, 5, 5); // top, left, bottom, right
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
    // Optional: no border
  }

  @Override
  protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
      int tabIndex, String title, Rectangle textRect, boolean isSelected) {
    g.setFont(font);
    g.setColor(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
    g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
  }
}
