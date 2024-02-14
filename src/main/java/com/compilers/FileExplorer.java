package com.compilers;

import javax.swing.*;
import java.io.File;
import java.util.Optional;

public final class FileExplorer {

    public static Optional<File> selectFile() {
        var fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return Optional.of(fileChooser.getSelectedFile());
        }

        return Optional.empty();
    }
}
