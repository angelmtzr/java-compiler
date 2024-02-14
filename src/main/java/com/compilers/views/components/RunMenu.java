package com.compilers.views.components;

import javax.swing.*;

public class RunMenu extends JMenu {
    public final JMenuItem lexerMenuItem;
    public final JMenuItem parserMenuItem;

    public RunMenu(String title) {
        super(title);

        lexerMenuItem = new JMenuItem("Run Lexer");
        parserMenuItem = new JMenuItem("Run Parser");

        add(lexerMenuItem);
        add(parserMenuItem);
    }
}
