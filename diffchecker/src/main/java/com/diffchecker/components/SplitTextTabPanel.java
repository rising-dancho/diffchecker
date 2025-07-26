package com.diffchecker.components;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SplitTextTabPanel extends JPanel {
    private static final String PACKAGE_NAME = "diffchecker";
    private final JTextArea jt1 = new JTextArea();
    private final JTextArea jt2 = new JTextArea();

    private final JLabel leftSummaryLabel = new JLabel();
    private final JLabel rightSummaryLabel = new JLabel();

    // ADD SUMMARY ARROWS
    private static int added = 0, removed = 0;

    // WORD HIGHLIGHT
    private static final Color DELETE_WORD_COLOR = new Color(0x40191D); // darker red
    private static final Color DELETE_WORD_COLOR_DARKER = new Color(0x8B1E1D); // darker red
    private static final Color ADD_WORD_COLOR = new Color(0x12342B); // darker green
    private static final Color ADD_WORD_COLOR_DARKER = new Color(0x137B5A);

    // FONT COLORS
    private static final Color editorBg = new Color(0x17181C); // Dark gray
    private static final Color editorFg = new Color(0xD4D4D4); // Light text

    // BACKGROUND COLOR
    // private static final Color BACKGROUND_LIGHT = new Color(0xF9FAFA);
    private final Color BACKGROUND_DARK = new Color(36, 37, 38);
    private final Color BACKGROUND_LABEL_DARK = new Color(0x1E1E1E);

    // SUMMARY FONT COLOR
    private static final Color REMOVAL_LABEL_COLOR_DARK = new Color(0xB83A3A); // darker red
    private static final Color ADDED_LABEL_COLOR_DARK = new Color(0x1C7758); // darker red

    // BUTTON COLOR AND HOVER COLOR
    private static final Color BTN_COLOR = new Color(0x00af74);
    private static final Color BTN_COLOR_DARKER = new Color(0x00744d);

    // SCROLL BARS
    private final JScrollPane scroll1;
    private final JScrollPane scroll2;

    // SUMMARY LABELS
    private final JPanel leftLabelPanel;
    private final JPanel rightLabelPanel;

    public SplitTextTabPanel() {
        setLayout(new BorderLayout());

        scroll1 = new JScrollPane(jt1);
        scroll2 = new JScrollPane(jt2);

        // CUSTOM SCROLLBARS
        scroll1.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scroll2.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scroll1.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scroll2.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        scroll1.getHorizontalScrollBar().setOpaque(true);
        scroll1.getHorizontalScrollBar().setBackground(editorBg); // Match your dark theme

        scroll2.getHorizontalScrollBar().setOpaque(true);
        scroll2.getHorizontalScrollBar().setBackground(editorBg);
        scroll1.getVerticalScrollBar().setOpaque(true);
        scroll1.getVerticalScrollBar().setBackground(editorBg);

        scroll2.getVerticalScrollBar().setOpaque(true);
        scroll2.getVerticalScrollBar().setBackground(editorBg);

        scroll1.setOpaque(false);
        scroll1.getViewport().setOpaque(false);

        scroll2.setOpaque(false);
        scroll2.getViewport().setOpaque(false);

        // FONT FAMILY OF THE TEXTAREAS
        try {
            InputStream is = getClass().getResourceAsStream(
                    "/" + PACKAGE_NAME + "/fonts/FiraCode-Regular.ttf");
            Font firaCode = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 16f);

            jt1.setFont(firaCode);
            jt2.setFont(firaCode);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Fallback if font fails to load
            jt1.setFont(new Font("Monospaced", Font.PLAIN, 14));
            jt2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        }

        // REMOVE DEFAULT BORDERS
        jt1.setBorder(BorderFactory.createEmptyBorder());
        jt2.setBorder(BorderFactory.createEmptyBorder());

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

        scroll1.setBorder(null);
        scroll2.setBorder(null);
        // scroll2.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // Create label panel for each text area
        leftLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftLabelPanel.add(leftSummaryLabel);
        leftLabelPanel.setBackground(BACKGROUND_LABEL_DARK);

        rightLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightLabelPanel.add(rightSummaryLabel);
        rightLabelPanel.setBackground(BACKGROUND_LABEL_DARK);

        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(leftLabelPanel, BorderLayout.NORTH);
        p1.add(scroll1, BorderLayout.CENTER);

        JPanel p2 = new JPanel(new BorderLayout());
        p2.add(rightLabelPanel, BorderLayout.NORTH);
        p2.add(scroll2, BorderLayout.CENTER);

        // SIDE BY SIDE TEXT AREAS
        JPanel sideBySidePanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 10px gap between areas
        sideBySidePanel.setBackground(BACKGROUND_DARK); // Match theme
        sideBySidePanel.add(p1);
        sideBySidePanel.add(p2);

        // add(splitPane, BorderLayout.CENTER);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // top, left, bottom, right
        contentPanel.setBackground(BACKGROUND_DARK); // match your theme
        contentPanel.add(sideBySidePanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // CUSTOM BUTTON
        RoundedButton diffcheckBtn = new RoundedButton("Find Difference");
        diffcheckBtn.setBackgroundColor(BTN_COLOR); // <- normal color
        diffcheckBtn.setHoverBackgroundColor(BTN_COLOR_DARKER); // <- hover color
        diffcheckBtn.setBorderColor(BTN_COLOR);// <- normal color
        diffcheckBtn.setHoverBorderColor(BTN_COLOR_DARKER); // <- hover color
        diffcheckBtn.setBorderThickness(2);
        diffcheckBtn.setCornerRadius(10);
        diffcheckBtn.addActionListener(e -> highlightDiffs());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // CENTER = button centered
        bottomPanel.setBackground(BACKGROUND_DARK);
        // bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        bottomPanel.add(diffcheckBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add labels above each scroll pane
        p1.add(leftLabelPanel, BorderLayout.NORTH);
        p2.add(rightLabelPanel, BorderLayout.NORTH);

        // INITIALLY HIDE SUMMARY LABELS
        leftLabelPanel.setVisible(false);
        rightLabelPanel.setVisible(false);

        jt1.setBackground(editorBg);
        jt1.setForeground(editorFg);
        jt1.setCaretColor(editorFg); // make the cursor visible

        jt2.setBackground(editorBg);
        jt2.setForeground(editorFg);
        jt2.setCaretColor(editorFg);

        // REMOVING THE WHITE SQUARES AT THE INTERSCTION OF THE SCROLLBARS
        // Unique corners for scroll1
        JPanel scroll1CornerLeft = new JPanel();
        scroll1CornerLeft.setBackground(editorBg);
        JPanel scroll1CornerRight = new JPanel();
        scroll1CornerRight.setBackground(editorBg);

        // Unique corners for scroll2
        JPanel scroll2CornerLeft = new JPanel();
        scroll2CornerLeft.setBackground(editorBg);
        JPanel scroll2CornerRight = new JPanel();
        scroll2CornerRight.setBackground(editorBg);

        // Set corners for scroll1
        scroll1.setCorner(JScrollPane.LOWER_LEFT_CORNER, scroll1CornerLeft);
        scroll1.setCorner(JScrollPane.LOWER_RIGHT_CORNER, scroll1CornerRight);

        // Set corners for scroll2
        scroll2.setCorner(JScrollPane.LOWER_LEFT_CORNER, scroll2CornerLeft);
        scroll2.setCorner(JScrollPane.LOWER_RIGHT_CORNER, scroll2CornerRight);

        // ------------- end

        // Scroll panes (optional, matches textarea bg)
        scroll1.getViewport().setBackground(editorBg);
        scroll2.getViewport().setBackground(editorBg);

        // Background of the left/right label panels
        leftLabelPanel.setBackground(editorBg);
        rightLabelPanel.setBackground(editorBg);
    }

    private void highlightDiffs() {
        jt1.getHighlighter().removeAllHighlights();
        jt2.getHighlighter().removeAllHighlights();

        // SHOW SUMMARY LABELS
        leftLabelPanel.setVisible(true);
        rightLabelPanel.setVisible(true);

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
                    int max = Math.max(origLines.size(), revLines.size());

                    for (int i = 0; i < max; i++) {
                        String leftLine = i < origLines.size() ? origLines.get(i) : "";
                        String rightLine = i < revLines.size() ? revLines.get(i) : "";

                        if (!leftLine.isBlank()) {
                            aligned1.add("- " + leftLine);
                            removed++;
                        } else {
                            aligned1.add(""); // Blank line for alignment
                        }

                        if (!rightLine.isBlank()) {
                            aligned2.add("+ " + rightLine);
                            added++;
                        } else {
                            aligned2.add(""); // Blank line for alignment
                        }

                        origIndex += i < origLines.size() ? 1 : 0;
                        revIndex += i < revLines.size() ? 1 : 0;
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

        // Set the diff summary in label (you can style it too)
        leftSummaryLabel.setText(String.format("❌ %d removals", removed));
        rightSummaryLabel.setText(String.format("✔️ %d additions", added));
        // LABEL TEXT COLOR
        leftSummaryLabel.setForeground(REMOVAL_LABEL_COLOR_DARK); // red
        rightSummaryLabel.setForeground(ADDED_LABEL_COLOR_DARK); // green
        // LABEL FONT SIZE
        leftSummaryLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        rightSummaryLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        jt1.setText(String.join("\n", aligned1));
        jt2.setText(String.join("\n", aligned2));

        // Must be after setting text so offsets are accurate
        for (int i = 0; i < aligned1.size(); i++) {
            if (aligned1.get(i).startsWith("- ") && aligned2.get(i).startsWith("+ ")) {
                String left = aligned1.get(i).substring(2);
                String right = aligned2.get(i).substring(2);
                highlightIntraLineDiff(left, right, jt1, i, true);
                highlightIntraLineDiff(left, right, jt2, i, false);
            }
        }

        highlightLines(jt1, "- ", DELETE_WORD_COLOR);
        highlightLines(jt2, "+ ", ADD_WORD_COLOR);

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

    private void highlightIntraLineDiff(String line1, String line2, JTextArea area, int lineIndex, boolean isLeft) {
        List<String> tokens1 = List.of(line1.split("\\b"));
        List<String> tokens2 = List.of(line2.split("\\b"));

        Patch<String> wordPatch = DiffUtils.diff(tokens1, tokens2);

        // Base color based on side
        Color inlineColor;
        if (line1.equals(line2))
            return; // skip if not changed

        if (isLeft && !line2.isBlank()) {
            inlineColor = DELETE_WORD_COLOR_DARKER;
        } else if (!isLeft && !line1.isBlank()) {
            inlineColor = ADD_WORD_COLOR_DARKER;
        } else {
            inlineColor = isLeft ? DELETE_WORD_COLOR_DARKER : ADD_WORD_COLOR_DARKER;
        }

        Highlighter highlighter = area.getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(inlineColor);

        try {
            int lineStartOffset = area.getLineStartOffset(lineIndex);
            int pos = lineStartOffset + 2; // Skip "- " or "+ "

            List<String> tokens = isLeft ? tokens1 : tokens2;

            for (String token : tokens) {
                boolean changed = wordPatch.getDeltas().stream()
                        .anyMatch(delta -> (isLeft ? delta.getSource().getLines() : delta.getTarget().getLines())
                                .contains(token));

                if (changed && !token.isBlank()) {
                    highlighter.addHighlight(pos, pos + token.length(), painter);
                }
                pos += token.length();
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}