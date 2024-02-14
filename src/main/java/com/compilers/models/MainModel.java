package com.compilers.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class MainModel {
    public String getFileContents(File file) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(file.toURI())));
    }

    public void writeToFile(File file, String content) throws IOException {
        Files.write(Paths.get(file.toURI()), content.getBytes());
    }
}
