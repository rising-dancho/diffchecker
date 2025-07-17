package com.diffchecker.components;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.*;

public class SplitTextTabPanel extends JPanel {
    private final JTextArea jt1 = new JTextArea();
    private final JTextArea jt2 = new JTextArea();
    private final JSplitPane splitPane;

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

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p1, p2);
        splitPane.setDividerSize(1); // Thin divider
        splitPane.setEnabled(false); // Disable user interaction
        splitPane.setContinuousLayout(true); // Smooth resizing
        splitPane.setResizeWeight(0.5); // Split equally when resizing
        splitPane.setDividerLocation(0.5); // Initial position (will be overridden by layout)

        // Override layout to keep divider centered always
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                splitPane.setDividerLocation(0.5); // Force center after resize
            }
        });

        // CUSTOM BUTTON
        RoundedButton findDiffBtn = new RoundedButton("Find Difference");
        findDiffBtn.setBackgroundColor(new Color(0x00C281));
        findDiffBtn.setHoverBackgroundColor(new Color(0x009966)); // <- hover color
        findDiffBtn.setTextColor(Color.WHITE);
        findDiffBtn.setBorderColor(new Color(0x00C281));
        findDiffBtn.setHoverBorderColor(new Color(0x009966));
        findDiffBtn.setBorderThickness(2);
        findDiffBtn.setCornerRadius(10);
        findDiffBtn.addActionListener(e -> highlightMatchingText());

        add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // CENTER = button centered
        bottomPanel.setOpaque(false); // To inherit dark background
        bottomPanel.add(findDiffBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Highlight matching text
    private void highlightMatchingText() {
        // Clear previous highlights
        jt1.getHighlighter().removeAllHighlights();
        jt2.getHighlighter().removeAllHighlights();

        String text1 = jt1.getText();
        String text2 = jt2.getText();

        String[] lines1 = text1.split("\\R"); // split on newlines
        String[] lines2 = text2.split("\\R");

        int minLines = Math.min(lines1.length, lines2.length);

        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(0x80FF80)); // light
                                                                                                                    // green

        try {
            for (int i = 0; i < minLines; i++) {
                if (lines1[i].equals(lines2[i])) {
                    int start1 = getLineStartOffset(jt1, i);
                    int end1 = start1 + lines1[i].length();
                    jt1.getHighlighter().addHighlight(start1, end1, painter);

                    int start2 = getLineStartOffset(jt2, i);
                    int end2 = start2 + lines2[i].length();
                    jt2.getHighlighter().addHighlight(start2, end2, painter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getLineStartOffset(JTextArea textArea, int line) throws Exception {
        return textArea.getLineStartOffset(line);
    }

}
