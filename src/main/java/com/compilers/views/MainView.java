package com.compilers.views;

import com.compilers.Token;
import com.compilers.views.components.MenuBar;
import com.compilers.views.components.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class MainView extends JFrame {
    public final MenuBar menuBar;
    public final OutputConsole outputConsole;
    public final CodeEditor codeEditor;
    private final JPanel infoPanel;

    public MainView() {
        super("Java Compiler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(new GridLayout(2, 1));

        // Menu bar with File and Run menu
        menuBar = new MenuBar(new FileMenu("File"), new RunMenu("Run"));
        setJMenuBar(menuBar);

        // Top panel with editor and info panels
        var topPanel = new JPanel(new GridLayout(1, 2));
        codeEditor = new CodeEditor();
        infoPanel = new JPanel();
        infoPanel.setBorder(new TitledBorder("Info"));
        topPanel.add(new JScrollPane(codeEditor));
        topPanel.add(new JScrollPane(infoPanel));
        add(topPanel);

        // Output console
        outputConsole = new OutputConsole();
        add(new JScrollPane(outputConsole));

        setVisible(true);
    }

    public void showTokensTable(Vector<Token> tokens) {
        try {
            infoPanel.remove(0);
        } catch (ArrayIndexOutOfBoundsException ex) {
            // First time it is created it will fail
        }
        var columnNames = new String[]{"Line", "Token Type", "Word"};
        var tokensTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (var token : tokens) {
            tokensTableModel.addRow(new Object[]{token.line(), token.type(), token.value()});
        }
        infoPanel.add(new JScrollPane(new JTable(tokensTableModel)), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}