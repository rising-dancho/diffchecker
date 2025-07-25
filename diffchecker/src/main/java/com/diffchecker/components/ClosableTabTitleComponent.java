package com.diffchecker.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClosableTabTitleComponent extends JPanel {
    private final Color TAB_TEXT_COLOR = new Color(0x888690);

    public ClosableTabTitleComponent(JTabbedPane tabbedPane, String title, Runnable onTabEmptyFallback) {
        super(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TAB_TEXT_COLOR);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        ImageIcon iconDefault = new ImageIcon(getClass().getResource("/diffchecker/images/close_def.png"));
        ImageIcon iconHover = new ImageIcon(getClass().getResource("/diffchecker/images/close_hover.png"));

        ImageIcon defaultIcon = new ImageIcon(iconDefault.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        ImageIcon hoverIcon = new ImageIcon(iconHover.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

        JButton closeButton = new JButton(defaultIcon);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setMargin(new Insets(0, 5, 0, 5));

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

        closeButton.addActionListener(e -> {
            int index = tabbedPane.indexOfTabComponent(this);
            if (index != -1) {
                tabbedPane.remove(index);
                if (tabbedPane.getTabCount() == 1 && onTabEmptyFallback != null) {
                    onTabEmptyFallback.run();
                }
            }
        });

        add(titleLabel);
        add(closeButton);
    }
}
