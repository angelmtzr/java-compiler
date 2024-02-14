package com.compilers.views.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class OutputConsole extends JTextPane {
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color FOREGROUND_COLOR = Color.WHITE;

    public OutputConsole() {
        setEditable(false);
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        setLayout(new FlowLayout());
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        // Title
        var titleBorder = new TitledBorder("Output");
        titleBorder.setTitleColor(FOREGROUND_COLOR);
        setBorder(titleBorder);
    }

    public void appendText(String text) {
        setText(getText() + text);
    }
}
