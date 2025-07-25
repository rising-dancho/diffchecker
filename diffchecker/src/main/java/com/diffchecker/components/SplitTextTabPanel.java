package com.diffchecker.components;

import javax.swing.*;
import javax.swing.text.BadLocationException;
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
    private final JLabel summaryLabel = new JLabel("Summary: ");

    // scroll bars
    private final JScrollPane scroll1;
    private final JScrollPane scroll2;

    public SplitTextTabPanel() {
        setLayout(new BorderLayout());

        scroll1 = new JScrollPane(jt1);
        scroll2 = new JScrollPane(jt2);

        jt1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        jt2.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Synchronize vertical scrolling
        JScrollBar vBar1 = scroll1.getVerticalScrollBar();
        JScrollBar vBar2 = scroll2.getVerticalScrollBar();

        vBar1.addAdjustmentListener(e -> {
            if (vBar2.getValue() != vBar1.getValue()) {
                vBar2.setValue(vBar1.getValue());
            }
        });

        vBar2.addAdjustmentListener(e -> {
            if (vBar1.getValue() != vBar2.getValue()) {
                vBar1.setValue(vBar2.getValue());
            }
        });

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
        RoundedButton diffcheckBtn = new RoundedButton("Find Difference");
        diffcheckBtn.setBackgroundColor(new Color(0x00C281));
        diffcheckBtn.setHoverBackgroundColor(new Color(0x009966)); // <- hover color
        diffcheckBtn.setTextColor(Color.WHITE);
        diffcheckBtn.setBorderColor(new Color(0x00C281));
        diffcheckBtn.setHoverBorderColor(new Color(0x009966));
        diffcheckBtn.setBorderThickness(2);
        diffcheckBtn.setCornerRadius(10);
        diffcheckBtn.addActionListener(e -> highlightDiffs());

        add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // CENTER = button centered
        bottomPanel.setOpaque(false); // To inherit dark background
        bottomPanel.add(diffcheckBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Create top panel for summary
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(summaryLabel);
        add(topPanel, BorderLayout.NORTH); // <-- Move to top
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

        // ADD SUMMARY ARROWS
        int added = 0, removed = 0, changed = 0;

        for (AbstractDelta<String> delta : patch.getDeltas()) {
            switch (delta.getType()) {
                case INSERT -> added++;
                case DELETE -> removed++;
                case CHANGE -> changed++;
                default -> throw new IllegalArgumentException("Unexpected value: " + delta.getType());
            }
        }

        // Set the diff summary in label (you can style it too)
        summaryLabel.setText(String.format(
                "Summary: ✔️ %d added   ❌ %d removed   🔄 %d changed",
                added, removed, changed));

        jt1.setText(String.join("\n", aligned1));
        jt2.setText(String.join("\n", aligned2));

        highlightLines(jt1, "- ", new Color(0xF29D9E));
        highlightLines(jt2, "+ ", new Color(0x81DBBE));
        highlightLines(jt1, "- ", new Color(0xFFF59D)); // pale yellow
        highlightLines(jt2, "+ ", new Color(0xFFF59D)); // same for changed lines

        // Repaint to ensure layout is updated
        jt1.revalidate();
        jt2.revalidate();

        // always show scrollbars but synced
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // AUTO SCROLL TO THE FIRST CHANGE
        List<AbstractDelta<String>> deltas = patch.getDeltas();

        if (!deltas.isEmpty()) {
            AbstractDelta<String> firstDelta = deltas.get(0);
            int firstDiffLine = firstDelta.getSource().getPosition();

            // Scroll after highlighting and rendering
            SwingUtilities.invokeLater(() -> {
                try {
                    int startOffset = jt1.getDocument().getDefaultRootElement().getElement(firstDiffLine)
                            .getStartOffset();
                    @SuppressWarnings("deprecation")
                    Rectangle viewRect = jt1.modelToView(startOffset);
                    if (viewRect != null) {
                        jt1.scrollRectToVisible(viewRect);
                        jt2.scrollRectToVisible(viewRect); // Optional sync
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            });
        }
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