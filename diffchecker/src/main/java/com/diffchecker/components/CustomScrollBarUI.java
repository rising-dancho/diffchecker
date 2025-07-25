package com.diffchecker.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {

  private Color thumbColor = new Color(0x636363); // Light gray
  private Color trackColor = new Color(0x17181C);
  private Color thumbHoverColor = new Color(0x8B8B8B); // Darker on hover


  @Override
  protected void configureScrollBarColors() {
    this.thumbColor = this.thumbColor;
    this.trackColor = this.trackColor;
  }

  // THINNER SCROLLBAR
  @Override
  protected Dimension getMinimumThumbSize() {
    // Ensure thumb is still visible and usable
    return new Dimension(30, 30); // Width/Height depending on orientation
  }

  @Override
  public Dimension getPreferredSize(JComponent c) {
    if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
      return new Dimension(10, super.getPreferredSize(c).height); // 🔧 thinner vertical bar
    } else {
      return new Dimension(super.getPreferredSize(c).width, 10); // 🔧 thinner horizontal bar
    }
  }

  // SPACE AROUND THE SCROLLBAR
  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(isThumbRollover() ? thumbHoverColor : thumbColor);

    // Add some inset (shrink the thumb a bit)
    int inset = 1;
    int arc = 10;
    g2.fillRoundRect(thumbBounds.x + inset, thumbBounds.y + inset,
        thumbBounds.width - 2 * inset, thumbBounds.height - 2 * inset, arc, arc);
    g2.dispose();
  }

  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    g.setColor(trackColor);
    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
  }

  @Override
  protected JButton createDecreaseButton(int orientation) {
    return createZeroButton();
  }

  @Override
  protected JButton createIncreaseButton(int orientation) {
    return createZeroButton();
  }

  private JButton createZeroButton() {
    JButton button = new JButton();
    button.setPreferredSize(new Dimension(0, 0));
    button.setMinimumSize(new Dimension(0, 0));
    button.setMaximumSize(new Dimension(0, 0));
    return button;
  }
}
