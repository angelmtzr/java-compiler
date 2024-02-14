package com.compilers.views.components;

import javax.swing.*;

public final class FileMenu extends JMenu {
    public final JMenuItem newFileMenuItem;
    public final JMenuItem openFileMenuItem;
    public final JMenuItem saveFileMenuItem;
    public final JMenuItem exitMenuItem;

    public FileMenu(String title) {
        super(title);

        newFileMenuItem = new JMenuItem("New");
        openFileMenuItem = new JMenuItem("Open");
        saveFileMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");

        add(newFileMenuItem);
        add(openFileMenuItem);
        add(saveFileMenuItem);
        addSeparator();
        add(exitMenuItem);
    }
}
