package com.compilers.views.components;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public final FileMenu fileMenu;
    public final RunMenu runMenu;

    public MenuBar(FileMenu fileMenu, RunMenu runMenu) {
        this.fileMenu = fileMenu;
        this.runMenu = runMenu;

        add(fileMenu);
        add(runMenu);
    }

}
