package com.diffchecker.components;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

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

        List<String> original = List.of(jt1.getText().split("\\R"));
        List<String> revised = List.of(jt2.getText().split("\\R"));

        Patch<String> patch = DiffUtils.diff(original, revised);

        List<String> aligned1 = new ArrayList<>();
        List<String> aligned2 = new ArrayList<>();

        int origIndex = 0, revIndex = 0;

        for (AbstractDelta<String> delta : patch.getDeltas()) {
            int origPos = delta.getSource().getPosition();
            int revPos = delta.getTarget().getPosition();

            // Unchanged before the delta
            while (origIndex < origPos && revIndex < revPos) {
                aligned1.add("  " + original.get(origIndex++));
                aligned2.add("  " + revised.get(revIndex++));
            }

            List<String> origLines = delta.getSource().getLines();
            List<String> revLines = delta.getTarget().getLines();

            switch (delta.getType()) {
                case DELETE -> {
                    for (String line : origLines) {
                        aligned1.add("- " + line);
                        aligned2.add("");
                        origIndex++;
                    }
                }
                case INSERT -> {
                    for (String line : revLines) {
                        aligned1.add("");
                        aligned2.add("+ " + line);
                        revIndex++;
                    }
                }
                case CHANGE -> {
                    for (String line : origLines) {
                        aligned1.add("- " + line);
                        origIndex++;
                    }
                    for (String line : revLines) {
                        aligned2.add("+ " + line);
                        revIndex++;
                    }
                }
                default -> throw new IllegalArgumentException("Unexpected value: " + delta.getType());
            }
        }

        // Remaining unchanged
        while (origIndex < original.size() && revIndex < revised.size()) {
            aligned1.add("  " + original.get(origIndex++));
            aligned2.add("  " + revised.get(revIndex++));
        }

        jt1.setText(String.join("\n", aligned1));
        jt2.setText(String.join("\n", aligned2));

        highlightLines(jt1, "- ", new Color(0xF29D9E));
        highlightLines(jt2, "+ ", new Color(0x81DBBE));
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