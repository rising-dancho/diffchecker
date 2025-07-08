package com.diffchecker.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class LineNumberingTextArea extends JTextArea implements DocumentListener {
    private final JTextArea textArea;

    public LineNumberingTextArea(JTextArea textArea) {
        this.textArea = textArea;
        textArea.getDocument().addDocumentListener(this);
        setEditable(false);
        setBackground(Color.LIGHT_GRAY);
        setFont(textArea.getFont());
        updateLineNumbers();
    }

    private void updateLineNumbers() {
        StringBuilder lineNumbersText = new StringBuilder();
        int lines = textArea.getLineCount();
        for (int i = 1; i <= lines; i++) {
            lineNumbersText.append(i).append(System.lineSeparator());
        }
        setText(lineNumbersText.toString());
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateLineNumbers();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateLineNumbers();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateLineNumbers();
    }
}
