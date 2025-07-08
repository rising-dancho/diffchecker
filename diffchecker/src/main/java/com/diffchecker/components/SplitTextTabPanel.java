package com.diffchecker.components;

import javax.swing.*;
import java.awt.*;

public class SplitTextTabPanel extends JPanel {
    private final JTextArea jt1 = new JTextArea();
    private final JTextArea jt2 = new JTextArea();

    public SplitTextTabPanel() {
        setLayout(new BorderLayout());

        JScrollPane scroll1 = new JScrollPane(jt1);
        JScrollPane scroll2 = new JScrollPane(jt2);

        scroll1.setRowHeaderView(new LineNumberingTextArea(jt1));
        scroll2.setRowHeaderView(new LineNumberingTextArea(jt2));

        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(scroll1, BorderLayout.CENTER);

        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(scroll2, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
        splitPane.setDividerLocation(540);

        JButton copyBtn = new JButton("Find Difference");
        copyBtn.addActionListener(e -> jt2.setText(jt1.getText()));

        add(splitPane, BorderLayout.CENTER);
        add(copyBtn, BorderLayout.SOUTH);
    }
}
