package com.diffchecker.components;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockDiffViewer extends JFrame {

  private JTextPane leftPane = new JTextPane();
  private JTextPane rightPane = new JTextPane();

  public BlockDiffViewer(String[] leftLines, String[] rightLines) {
    setTitle("Block Diff Viewer");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1000, 600);
    setLocationRelativeTo(null);

    leftPane.setEditable(false);
    rightPane.setEditable(false);

    JScrollPane leftScroll = new JScrollPane(leftPane);
    JScrollPane rightScroll = new JScrollPane(rightPane);
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, rightScroll);
    splitPane.setDividerLocation(500);

    add(splitPane);
    displayDiff(leftLines, rightLines);
  }

  private void displayDiff(String[] leftLines, String[] rightLines) {
    StyledDocument docLeft = leftPane.getStyledDocument();
    StyledDocument docRight = rightPane.getStyledDocument();

    SimpleAttributeSet red = new SimpleAttributeSet();
    StyleConstants.setBackground(red, new Color(255, 200, 200));

    SimpleAttributeSet green = new SimpleAttributeSet();
    StyleConstants.setBackground(green, new Color(200, 255, 200));

    SimpleAttributeSet gray = new SimpleAttributeSet();
    StyleConstants.setBackground(gray, new Color(230, 230, 230));

    SimpleAttributeSet normal = new SimpleAttributeSet();

    int i = 0, j = 0;
    while (i < leftLines.length || j < rightLines.length) {
      if (i < leftLines.length && leftLines[i].startsWith("-")) {
        // Start of a removed block
        List<String> removedBlock = new ArrayList<>();
        while (i < leftLines.length && leftLines[i].startsWith("-")) {
          removedBlock.add(leftLines[i]);
          i++;
        }

        for (String line : removedBlock) {
          insertWithAttr(docLeft, line + "\n", red);
        }
        for (int k = 0; k < removedBlock.size(); k++) {
          insertWithAttr(docRight, " \n", gray);
        }
      } else if (j < rightLines.length && rightLines[j].startsWith("+")) {
        // Start of an added block
        List<String> addedBlock = new ArrayList<>();
        while (j < rightLines.length && rightLines[j].startsWith("+")) {
          addedBlock.add(rightLines[j]);
          j++;
        }

        for (int k = 0; k < addedBlock.size(); k++) {
          insertWithAttr(docLeft, " \n", gray);
        }
        for (String line : addedBlock) {
          insertWithAttr(docRight, line + "\n", green);
        }
      } else {
        // Context or unchanged lines
        if (i < leftLines.length && j < rightLines.length) {
          insertWithAttr(docLeft, leftLines[i] + "\n", normal);
          insertWithAttr(docRight, rightLines[j] + "\n", normal);
          i++;
          j++;
        } else if (i < leftLines.length) {
          insertWithAttr(docLeft, leftLines[i++] + "\n", normal);
          insertWithAttr(docRight, " \n", gray);
        } else {
          insertWithAttr(docLeft, " \n", gray);
          insertWithAttr(docRight, rightLines[j++] + "\n", normal);
        }
      }
    }
  }

  private void insertWithAttr(StyledDocument doc, String line, AttributeSet attr) {
    try {
      doc.insertString(doc.getLength(), line, attr);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }

  // Example usage
  public static void main(String[] args) {
    String[] left = {
        "int x = 1;",
        "-int y = 2;",
        "-int z = x + y;",
        "System.out.println(x);"
    };

    String[] right = {
        "int x = 1;",
        "+int y = 100;",
        "+int z = x + y + 1;",
        "System.out.println(x);"
    };

    SwingUtilities.invokeLater(() -> {
      new BlockDiffViewer(left, right).setVisible(true);
    });
  }
}
