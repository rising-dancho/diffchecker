package com.diffchecker.java_fundamentals.swing_and_awt;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class JTabbed_pane extends JFrame {
  public static void main(String[] args) {
    new JTabbed_pane();
  }

  public JTabbed_pane() {
    setTitle("Tabs");
    setSize(1080, 720);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setFocusable(false);

    // Popup menu Delete Tab
    tabbedPane.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
      }

      private void maybeShowPopup(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
          if (e.isPopupTrigger()) {
            int index = tabbedPane.indexAtLocation(e.getX(), e.getY());
            if (index != -1 && index != 0) {
              JPopupMenu popup = new JPopupMenu();
              JMenuItem delete = new JMenuItem("Delete Tab");
              delete.addActionListener(ev -> tabbedPane.remove(index));
              popup.add(delete);
              popup.show(e.getComponent(), e.getX(), e.getY());
            }
          }
        }
      }

    });

    JButton addButton = new JButton("+");
    addButton.setBorder(null);
    addButton.setFocusPainted(false);
    addButton.setContentAreaFilled(false);
    addButton.setPreferredSize(new Dimension(30, 30));

    addButton.addActionListener(e -> addNewTab(tabbedPane));

    // Add "+" tab initially at the end
    tabbedPane.addTab("", null);
    tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, addButton);

    // Now add the first real tab before the "+"
    addNewTab(tabbedPane);

    add(tabbedPane);
    setVisible(true);
  }

  private JPanel createTabTitle(JTabbedPane tabbedPane, String title) {
    JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    tabPanel.setOpaque(false);

    JLabel titleLabel = new JLabel(title);

    // Default icon
    ImageIcon iconDefault = new ImageIcon(getClass().getResource("/diffchecker/images/close_def.png"));
    Image scaledDefault = iconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    ImageIcon defaultIcon = new ImageIcon(scaledDefault);

    // Hover icon
    ImageIcon iconHover = new ImageIcon(getClass().getResource("/diffchecker/images/close_hover.png"));
    Image scaledHover = iconHover.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    ImageIcon hoverIcon = new ImageIcon(scaledHover);

    // Button with default icon
    JButton closeButton = new JButton(defaultIcon);
    closeButton.setBorder(BorderFactory.createEmptyBorder());
    closeButton.setFocusPainted(false);
    closeButton.setMargin(new Insets(0, 5, 0, 5));
    closeButton.setFocusable(false);
    closeButton.setContentAreaFilled(false);

    // Hover effect
    closeButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        closeButton.setIcon(hoverIcon);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        closeButton.setIcon(defaultIcon);
      }
    });

    // Close tab on click
    closeButton.addActionListener(e -> {
      int index = tabbedPane.indexOfTabComponent(tabPanel);
      if (index != -1) {
        tabbedPane.remove(index);
        if (tabbedPane.getTabCount() == 1) { // only "+" button left
          addNewTab(tabbedPane);
        }
      }
    });

    tabPanel.add(titleLabel);
    tabPanel.add(closeButton);
    return tabPanel;
  }

  private int untitledCounter = 1;
  private void addNewTab(JTabbedPane tabbedPane) {
    JTextArea textArea = new JTextArea();
    int plusTabIndex = tabbedPane.getTabCount() - 1;

    // Create tab before the last "+" tab
    String title = "Untitled-" + untitledCounter++;
    tabbedPane.insertTab(title, null, new JScrollPane(textArea), null, plusTabIndex);
    tabbedPane.setTabComponentAt(plusTabIndex, createTabTitle(tabbedPane, title));
    tabbedPane.setSelectedIndex(plusTabIndex);
  }

}
