package com.compilers.controllers;

import com.compilers.FileExplorer;
import com.compilers.Lexer;
import com.compilers.Token;
import com.compilers.models.MainModel;
import com.compilers.views.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public final class MainController {
    private final MainView view;
    private final MainModel model;

    public MainController(MainModel model, MainView view) {
        this.view = view;
        this.model = model;

        // File Menu Actions
        view.menuBar.fileMenu.newFileMenuItem.addActionListener(this::newFile);
        view.menuBar.fileMenu.openFileMenuItem.addActionListener(this::openFile);
        view.menuBar.fileMenu.saveFileMenuItem.addActionListener(this::saveFile);
        view.menuBar.fileMenu.exitMenuItem.addActionListener(this::exit);

        // Run Menu Actions
        view.menuBar.runMenu.lexerMenuItem.addActionListener(this::lexer);
    }

    private void lexer(ActionEvent e) {
        view.outputConsole.appendText("Lexer started...\n");
        var tokens = Lexer.tokenize(view.codeEditor.getText());
        var words = tokens.size();
        var errors = tokens.stream().filter(Token::isError).count();
        view.showTokensTable(tokens);
        view.outputConsole.appendText("Words Found: " + words + "\n");
        view.outputConsole.appendText("Errors Found: " + errors + "\n");
        view.outputConsole.appendText("Lexer done.\n");
    }

    private void newFile(ActionEvent e) {
        view.codeEditor.setText("");
    }

    private void openFile(ActionEvent e) {
        var optionalFile = FileExplorer.selectFile();
        if (optionalFile.isEmpty())
            return;

        var file = optionalFile.get();

        try {
            var contents = model.getFileContents(file);
            view.codeEditor.setText(contents);
        } catch (IOException ex) {
            showMessageDialog(null, ex.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile(ActionEvent e) {
        var optionalFile = FileExplorer.selectFile();
        if (optionalFile.isEmpty())
            return;

        var file = optionalFile.get();
        var contents = view.codeEditor.getText();

        try {
            model.writeToFile(file, contents);
        } catch (IOException ex) {
            showMessageDialog(null, ex.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exit(ActionEvent e) {
        System.exit(0);
    }

}
