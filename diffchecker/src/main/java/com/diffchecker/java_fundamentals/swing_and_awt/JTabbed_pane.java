package com.diffchecker.java_fundamentals.swing_and_awt;

import java.awt.*;
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

    JButton addButton = new JButton("+");
    addButton.setBorder(null);
    addButton.setFocusPainted(false);
    addButton.setContentAreaFilled(false);
    addButton.setPreferredSize(new Dimension(30, 30));

    // Handle tab creation
    addButton.addActionListener(e -> {
      JTextArea textArea = new JTextArea();
      int index = tabbedPane.getTabCount() - 1;
      String title = "Untitled-" + tabbedPane.getTabCount();
      tabbedPane.insertTab(title, null, new JScrollPane(textArea), null, index);
      tabbedPane.setTabComponentAt(index, createTabTitle(tabbedPane, title));
      tabbedPane.setSelectedIndex(index);
    });

    // Add "+" tab at the end
    tabbedPane.addTab("", null); // placeholder for the "+" tab
    tabbedPane.setTabComponentAt(0, addButton);

    add(tabbedPane);
    setVisible(true);
  }

  private JPanel createTabTitle(JTabbedPane tabbedPane, String title) {
    JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    tabPanel.setOpaque(false);

    JLabel titleLabel = new JLabel(title);
    // close button
    ImageIcon originalIcon = new ImageIcon(getClass().getResource("/diffchecker/images/close_hover.png"));
    Image scaledImg = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);

    JButton closeButton = new JButton(new ImageIcon(scaledImg));
    closeButton.setBorder(BorderFactory.createEmptyBorder());
    closeButton.setFocusPainted(false);
    closeButton.setMargin(new Insets(0, 5, 0, 5));
    closeButton.setFocusable(false);
    closeButton.setContentAreaFilled(false);
    closeButton.setFont(closeButton.getFont().deriveFont(Font.BOLD));

    closeButton.addActionListener(e -> {
      int index = tabbedPane.indexOfTabComponent(tabPanel);
      if (index != -1) {
        tabbedPane.remove(index);
      }
    });

    tabPanel.add(titleLabel);
    tabPanel.add(closeButton);
    return tabPanel;
  }
}
