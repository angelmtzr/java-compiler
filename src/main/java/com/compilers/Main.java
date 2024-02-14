package com.compilers;

import com.compilers.controllers.MainController;
import com.compilers.models.MainModel;
import com.compilers.views.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController(new MainModel(), new MainView()));
    }
}