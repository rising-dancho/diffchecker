package com.diffchecker.components;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        RoundedButton copyBtn = new RoundedButton("Find Difference");
        copyBtn.setBackgroundColor(new Color(0x00C281));
        copyBtn.setHoverBackgroundColor(new Color(0x009966)); // <- hover color
        copyBtn.setTextColor(Color.WHITE);
        copyBtn.setBorderColor(new Color(0x00C281));
        copyBtn.setHoverBorderColor(new Color(0x009966));
        copyBtn.setBorderThickness(2);
        copyBtn.setCornerRadius(10);
        copyBtn.addActionListener(e -> highlightDiffs());

        add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // CENTER = button centered
        bottomPanel.setOpaque(false); // To inherit dark background
        bottomPanel.add(copyBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void highlightDiffs() {
        jt1.getHighlighter().removeAllHighlights();
        jt2.getHighlighter().removeAllHighlights();

        String[] lines1 = jt1.getText().split("\\R");
        String[] lines2 = jt2.getText().split("\\R");

        List<String> original = new ArrayList<>(List.of(lines1));
        List<String> modified = new ArrayList<>(List.of(lines2));

        List<String> aligned1 = new ArrayList<>();
        List<String> aligned2 = new ArrayList<>();

        int i = 0, j = 0;
        while (i < original.size() || j < modified.size()) {
            String line1 = i < original.size() ? original.get(i) : null;
            String line2 = j < modified.size() ? modified.get(j) : null;

            if (line1 != null && line2 != null && line1.equals(line2)) {
                aligned1.add("  " + line1);
                aligned2.add("  " + line2);
                i++;
                j++;
            } else if (line2 != null && (line1 == null || !original.subList(i, original.size()).contains(line2))) {
                // Added line
                aligned1.add("");
                aligned2.add("+ " + line2);
                j++;
            } else if (line1 != null && (line2 == null || !modified.subList(j, modified.size()).contains(line1))) {
                // Removed line
                aligned1.add("- " + line1);
                aligned2.add("");
                i++;
            } else {
                // Different but both exist → treat as changed
                aligned1.add("- " + line1);
                aligned2.add("+ " + line2);
                i++;
                j++;
            }
        }

        // Replace text areas with aligned content
        jt1.setText(String.join("\n", aligned1));
        jt2.setText(String.join("\n", aligned2));

        // Highlight removed in red and added in green
        highlightLines(jt1, "- ", new Color(0xF29D9E)); // light red
        highlightLines(jt2, "+ ", new Color(0x81DBBE)); // light green
    }

    private void highlightLines(JTextArea area, String prefix, Color color) {
        Highlighter highlighter = area.getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(color);

        String[] lines = area.getText().split("\\R");
        try {
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith(prefix)) {
                    int start = area.getLineStartOffset(i);
                    int end = area.getLineEndOffset(i);
                    highlighter.addHighlight(start, end, painter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
